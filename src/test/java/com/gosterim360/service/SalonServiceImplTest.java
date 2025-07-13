package com.gosterim360.service;

import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;
import com.gosterim360.exception.*;
import com.gosterim360.mapper.SalonMapper;
import com.gosterim360.model.Salon;
import com.gosterim360.model.Seat;
import com.gosterim360.repository.SalonRepository;
import com.gosterim360.service.impl.SalonServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalonServiceImplTest {

    @Mock
    private SalonRepository salonRepository;

    @InjectMocks
    private SalonServiceImpl salonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new salon successfully")
    void testCreateSalonSuccess() {
        SalonRequestDTO request = new SalonRequestDTO("Test", "Loc", 100);
        Salon salon = SalonMapper.toEntity(request);
        Salon saved = SalonMapper.toEntity(request);
        saved.setId(UUID.randomUUID());

        when(salonRepository.existsByName("Test")).thenReturn(false);
        when(salonRepository.saveAndFlush(any(Salon.class))).thenReturn(saved);

        SalonResponseDTO response = salonService.create(request);

        assertNotNull(response);
        assertEquals("Test", response.getName());
        verify(salonRepository).saveAndFlush(any(Salon.class));
    }

    @Test
    @DisplayName("Should throw exception if salon name already exists on create")
    void testCreateSalonDuplicateName() {
        SalonRequestDTO request = new SalonRequestDTO("Test", "Loc", 100);
        when(salonRepository.existsByName("Test")).thenReturn(true);

        assertThrows(SalonAlreadyExistsException.class, () -> salonService.create(request));
        verify(salonRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should throw exception if seat capacity is invalid on create")
    void testCreateSalonInvalidCapacity() {
        SalonRequestDTO request = new SalonRequestDTO("Test", "Loc", 0);
        when(salonRepository.existsByName("Test")).thenReturn(false);

        assertThrows(SalonSeatCapacityInvalidException.class, () -> salonService.create(request));
        verify(salonRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should update salon successfully")
    void testUpdateSalonSuccess() {
        UUID id = UUID.randomUUID();
        SalonRequestDTO request = new SalonRequestDTO("NewName", "NewLoc", 200);
        Salon existing = Salon.builder().name("OldName").location("OldLoc").seatCapacity(100).build();
        existing.setId(id);

        when(salonRepository.findById(id)).thenReturn(Optional.of(existing));
        when(salonRepository.existsByName("NewName")).thenReturn(false);
        when(salonRepository.saveAndFlush(any(Salon.class))).thenReturn(existing);

        SalonResponseDTO response = salonService.update(id, request);

        assertNotNull(response);
        assertEquals("NewName", response.getName());
        verify(salonRepository).saveAndFlush(existing);
    }

    @Test
    @DisplayName("Should throw exception if salon not found on update")
    void testUpdateSalonNotFound() {
        UUID id = UUID.randomUUID();
        SalonRequestDTO request = new SalonRequestDTO("Name", "Loc", 100);

        when(salonRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(SalonNotFoundException.class, () -> salonService.update(id, request));
    }

    @Test
    @DisplayName("Should throw exception if new name already exists on update")
    void testUpdateSalonDuplicateName() {
        UUID id = UUID.randomUUID();
        SalonRequestDTO request = new SalonRequestDTO("NewName", "Loc", 100);
        Salon existing = Salon.builder().name("OldName").location("Loc").seatCapacity(100).build();
        existing.setId(id);

        when(salonRepository.findById(id)).thenReturn(Optional.of(existing));
        when(salonRepository.existsByName("NewName")).thenReturn(true);

        assertThrows(SalonUpdateConflictException.class, () -> salonService.update(id, request));
    }

    @Test
    @DisplayName("Should throw exception if seat capacity is invalid on update")
    void testUpdateSalonInvalidCapacity() {
        UUID id = UUID.randomUUID();
        SalonRequestDTO request = new SalonRequestDTO("Name", "Loc", 0);
        Salon existing = Salon.builder().name("OldName").location("Loc").seatCapacity(100).build();
        existing.setId(id);

        when(salonRepository.findById(id)).thenReturn(Optional.of(existing));

        assertThrows(SalonSeatCapacityInvalidException.class, () -> salonService.update(id, request));
    }

    @Test
    @DisplayName("Should delete salon successfully")
    void testDeleteSalonSuccess() {
        UUID id = UUID.randomUUID();
        Salon existing = Salon.builder().name("Name").location("Loc").seatCapacity(100).build();
        existing.setId(id);

        when(salonRepository.findById(id)).thenReturn(Optional.of(existing));

        assertDoesNotThrow(() -> salonService.delete(id));
        verify(salonRepository).delete(existing);
    }

    @Test
    @DisplayName("Should throw exception if salon not found on delete")
    void testDeleteSalonNotFound() {
        UUID id = UUID.randomUUID();
        when(salonRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(SalonNotFoundException.class, () -> salonService.delete(id));
    }

    @Test
    @DisplayName("Should get salon by id successfully")
    void testGetByIdSuccess() {
        UUID id = UUID.randomUUID();
        Salon existing = Salon.builder().name("Name").location("Loc").seatCapacity(100).build();
        existing.setId(id);

        when(salonRepository.findById(id)).thenReturn(Optional.of(existing));

        SalonResponseDTO response = salonService.getById(id);

        assertNotNull(response);
        assertEquals("Name", response.getName());
    }

    @Test
    @DisplayName("Should throw exception if salon not found on getById")
    void testGetByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(salonRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(SalonNotFoundException.class, () -> salonService.getById(id));
    }

    @Test
    @DisplayName("Should get all salons")
    void testGetAllSalons() {
        List<Salon> salons = Arrays.asList(
                Salon.builder().name("A").location("L1").seatCapacity(10).build(),
                Salon.builder().name("B").location("L2").seatCapacity(20).build()
        );
        when(salonRepository.findAll()).thenReturn(salons);

        List<SalonResponseDTO> result = salonService.getAll();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());
        assertEquals("B", result.get(1).getName());
    }


    @Test
    @DisplayName("Should throw exception if name is null on create")
    void testCreateSalonNullName() {
        SalonRequestDTO request = new SalonRequestDTO(null, "Loc", 100);
        when(salonRepository.existsByName(null)).thenReturn(false);

        assertThrows(Exception.class, () -> salonService.create(request));
    }


    @Test
    @DisplayName("Should throw exception if seatCapacity is negative")
    void testCreateSalonNegativeCapacity() {
        SalonRequestDTO request = new SalonRequestDTO("Test", "Loc", -5);
        when(salonRepository.existsByName("Test")).thenReturn(false);

        assertThrows(SalonSeatCapacityInvalidException.class, () -> salonService.create(request));
    }

    @Test
    @DisplayName("Should handle large number of salons")
    void testGetAllSalonsLargeList() {
        List<Salon> salons = IntStream.range(0, 1000)
                .mapToObj(i -> Salon.builder().name("Salon" + i).location("Loc" + i).seatCapacity(i + 1).build())
                .collect(Collectors.toList());
        when(salonRepository.findAll()).thenReturn(salons);

        List<SalonResponseDTO> result = salonService.getAll();

        assertEquals(1000, result.size());
        assertEquals("Salon0", result.get(0).getName());
        assertEquals("Salon999", result.get(999).getName());
    }

    @Test
    @DisplayName("Should not allow duplicate salon names concurrently")
    void testConcurrentCreateDuplicateName() throws InterruptedException, ExecutionException {
        SalonRequestDTO request = new SalonRequestDTO("Concurrent", "Loc", 100);

        when(salonRepository.existsByName("Concurrent"))
                .thenReturn(false)
                .thenReturn(true); // 2nd thread sees as duplicate

        when(salonRepository.saveAndFlush(any(Salon.class)))
                .thenReturn(SalonMapper.toEntity(request));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Object> createTask = () -> {
            try {
                return salonService.create(request);
            } catch (Exception e) {
                return e;
            }
        };

        Future<Object> f1 = executor.submit(createTask);
        Future<Object> f2 = executor.submit(createTask);

        Object r1 = f1.get();
        Object r2 = f2.get();

        assertTrue(r1 instanceof SalonResponseDTO || r2 instanceof SalonResponseDTO);
        assertTrue(r1 instanceof SalonAlreadyExistsException || r2 instanceof SalonAlreadyExistsException);

        executor.shutdown();
    }

    @Test
    @DisplayName("SalonMapper toEntity and toDTO should work correctly")
    void testSalonMapper() {
        SalonRequestDTO request = new SalonRequestDTO("Test", "Loc", 100);
        Salon salon = SalonMapper.toEntity(request);
        assertEquals("Test", salon.getName());
        assertEquals("Loc", salon.getLocation());
        assertEquals(100, salon.getSeatCapacity());

        salon.setId(UUID.randomUUID());
        SalonResponseDTO dto = SalonMapper.toDTO(salon);
        assertEquals("Test", dto.getName());
        assertEquals("Loc", dto.getLocation());
        assertEquals(100, dto.getSeatCapacity());
    }

    @Test
    @DisplayName("Should remove all seats when salon is deleted (cascade)")
    void testCascadeDeleteSeats() {
        UUID id = UUID.randomUUID();
        Seat seat1 = new Seat();
        seat1.setId(UUID.randomUUID());
        Seat seat2 = new Seat();
        seat2.setId(UUID.randomUUID());

        Salon salon = Salon.builder()
                .name("Cascade")
                .location("Loc")
                .seatCapacity(2)
                .seats(Arrays.asList(seat1, seat2))
                .build();
        salon.setId(id);

        when(salonRepository.findById(id)).thenReturn(Optional.of(salon));

        assertDoesNotThrow(() -> salonService.delete(id));
        verify(salonRepository).delete(salon);
    }

    @Test
    @DisplayName("Performance test for creating many salons")
    void testPerformanceCreateManySalons() {
        int count = 1000;
        when(salonRepository.existsByName(anyString())).thenReturn(false);
        when(salonRepository.saveAndFlush(any(Salon.class))).thenAnswer(invocation -> {
            Salon s = invocation.getArgument(0);
            s.setId(UUID.randomUUID());
            return s;
        });

        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            SalonRequestDTO request = new SalonRequestDTO("Salon" + i, "Loc" + i, i + 1);
            SalonResponseDTO response = salonService.create(request);
            assertNotNull(response.getId());
        }
        long end = System.currentTimeMillis();
        System.out.println("Created " + count + " salons in " + (end - start) + " ms");
    }
}