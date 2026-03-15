package com.repetitajuavant.bikesharing;

import com.repetitajuavant.bikesharing.enums.IssueType;
import com.repetitajuavant.bikesharing.enums.TicketStatus;
import com.repetitajuavant.bikesharing.model.Bike;
import com.repetitajuavant.bikesharing.model.Station;
import com.repetitajuavant.bikesharing.model.Technician;
import com.repetitajuavant.bikesharing.model.Ticket;
import com.repetitajuavant.bikesharing.repository.BikeRepository;
import com.repetitajuavant.bikesharing.repository.StationRepository;
import com.repetitajuavant.bikesharing.repository.TechnicianRepository;
import com.repetitajuavant.bikesharing.repository.TicketRepository;
import com.repetitajuavant.bikesharing.service.AssignmentService;
import com.repetitajuavant.bikesharing.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BikeSharingApplicationTests - Suite di test per l'applicazione Bike Sharing
 * 
 * Testa:
 * - Creazione e gestione dei ticket
 * - Assegnazione intelligente dei tecnici
 * - Persistenza dei dati nel database H2
 */
@SpringBootTest
class BikeSharingApplicationTests {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AssignmentService assignmentService;

    private Bike testBike;
    private Technician testTech;
    private Station testStation;

    @BeforeEach
    void setUp() {
        // Pulisci i ticket prima di ogni test
        ticketRepository.deleteAll();

        // Crea una stazione di test
        testStation = new Station();
        testStation.setName("Test Station");
        testStation = stationRepository.save(testStation);

        // Crea una bici di test
        testBike = new Bike();
        testBike.setCode("TEST-BIKE-001");
        testBike.setStation(testStation);
        testBike = bikeRepository.save(testBike);

        // Verifica che il tecnico esiste
        List<Technician> technicians = technicianRepository.findAll();
        if (!technicians.isEmpty()) {
            testTech = technicians.get(0);
        }
    }

    @Test
    void testContextLoads() {
        // Verifica che il contesto Spring si carica correttamente
        assertNotNull(ticketService);
        assertNotNull(assignmentService);
    }

    @Test
    void testDataLoaderInitialized() {
        // Verifica che DataLoader ha caricato i dati iniziali
        assertEquals(5, technicianRepository.count());
        assertEquals(10, stationRepository.count());
        assertEquals(100, bikeRepository.count());
    }

    @Test
    void testCreateTicket() {
        // Test creazione di un ticket
        Ticket ticket = ticketService.createTicket(testBike.getId(), IssueType.BRAKES);

        assertNotNull(ticket);
        assertNotNull(ticket.getId());
        assertEquals(IssueType.BRAKES, ticket.getIssueType());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
        assertEquals(LocalDate.now(), ticket.getCreatedDate());
    }

    @Test
    void testUpdateTicketStatus() {
        // Test aggiornamento dello stato di un ticket
        Ticket ticket = ticketService.createTicket(testBike.getId(), IssueType.TIRES);
        
        // Aggiorna a IN_PROGRESS
        Ticket updated = ticketService.updateStatus(ticket.getId(), TicketStatus.IN_PROGRESS);
        assertEquals(TicketStatus.IN_PROGRESS, updated.getStatus());

        // Aggiorna a COMPLETED
        updated = ticketService.updateStatus(ticket.getId(), TicketStatus.COMPLETED);
        assertEquals(TicketStatus.COMPLETED, updated.getStatus());
        assertNotNull(updated.getCompletedDate());
    }

    @Test
    void testAssignmentService() {
        // Test dell'assegnazione automatica
        // Crea 3 ticket aperti
        Ticket ticket1 = ticketService.createTicket(testBike.getId(), IssueType.BRAKES);
        Ticket ticket2 = ticketService.createTicket(testBike.getId(), IssueType.TIRES);
        Ticket ticket3 = ticketService.createTicket(testBike.getId(), IssueType.LIGHTS);

        // Verifica che sono OPEN
        assertEquals(3, ticketRepository.findByStatus(TicketStatus.OPEN).size());

        // Esegui l'assegnazione
        assignmentService.assignTickets();

        // Verifica che i ticket sono stati assegnati
        Ticket assigned1 = ticketRepository.findById(ticket1.getId()).orElse(null);
        assertNotNull(assigned1);
        assertEquals(TicketStatus.ASSIGNED, assigned1.getStatus());
        assertNotNull(assigned1.getTechnician());
    }

    @Test
    void testMaxInterventionLimit() {
        // Test del limite massimo di 8 interventi per tecnico al giorno
        // Crea 10 ticket
        for (int i = 0; i < 10; i++) {
            ticketService.createTicket(testBike.getId(), IssueType.BRAKES);
        }

        // Assegna i ticket
        assignmentService.assignTickets();

        // Verifica che nessun tecnico abbia più di 8 interventi
        List<Technician> technicians = technicianRepository.findAll();
        for (Technician tech : technicians) {
            long count = tech.getTickets().stream()
                    .filter(t -> t.getCreatedDate().equals(LocalDate.now()))
                    .count();
            assertTrue(count <= 8, "Tecnico " + tech.getName() + " ha " + count + " interventi!");
        }
    }

    @Test
    void testBikePersistence() {
        // Test della persistenza dei dati delle bici
        List<Bike> allBikes = bikeRepository.findAll();
        assertEquals(100, allBikes.size());

        // Verifica che tutte le bici sono assegnate a una stazione
        for (Bike bike : allBikes) {
            assertNotNull(bike.getStation());
            assertNotNull(bike.getCode());
        }
    }

    @Test
    void testStationPersistence() {
        // Test della persistenza delle stazioni
        List<Station> stations = stationRepository.findAll();
        assertEquals(10, stations.size());

        // Verifica i nomi di alcuni stazioni note
        List<String> stationNames = stations.stream()
                .map(Station::getName)
                .toList();

        assertTrue(stationNames.contains("Centro"));
        assertTrue(stationNames.contains("Parco Principale"));
    }

    @Test
    void testFindBikesByStation() {
        // Test della ricerca di bici per stazione
        List<Bike> bikesInStation = bikeRepository.findByStation(testStation);
        
        assertNotNull(bikesInStation);
        assertTrue(bikesInStation.size() > 0);

        // Verifica che tutte le bici appartengono alla stazione corretta
        for (Bike bike : bikesInStation) {
            assertEquals(testStation.getId(), bike.getStation().getId());
        }
    }

    @Test
    void testTicketWithoutTechnician() {
        // Test che un ticket appena creato non ha un tecnico assegnato
        Ticket ticket = ticketService.createTicket(testBike.getId(), IssueType.LIGHTS);
        
        assertNull(ticket.getTechnician());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
    }

    @Test
    void testTicketCompletionDate() {
        // Test che la data di completamento si aggiorna correttamente
        Ticket ticket = ticketService.createTicket(testBike.getId(), IssueType.BRAKES);
        
        assertNull(ticket.getCompletedDate());

        // Completa il ticket
        Ticket completed = ticketService.updateStatus(ticket.getId(), TicketStatus.COMPLETED);
        
        assertNotNull(completed.getCompletedDate());
        assertEquals(LocalDate.now(), completed.getCompletedDate());
    }
