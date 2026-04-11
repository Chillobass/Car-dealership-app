package org.example;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComponentControllerTest /*extends PostgresIT*/ {
//    @LocalServerPort
//    private int port;
//
//    private WebTestClient webTestClient;
//
//    @Autowired
//    private ComponentTypeRepository componentTypeRepository;
//
//    @Autowired
//    private CarModelRepository carModelRepository;
//
//    @BeforeEach
//    public void setUp() {
//        webTestClient = WebTestClient.bindToServer()
//                .baseUrl("http://localhost:" + port).build();
//    }
//
//    @Test
//    void componentTestSuccess() {
//        var interior = new InteriorFactory().createComponent("alcantara");
//        var componentType = componentTypeRepository.save(interior);
//        var compSet = new HashSet<ComponentTypeEntity>();
//        var bmw = CarModelEntity.builder()
//                .name("M5 F90")
//                .brand("BMW")
//                .body(BodyType.SEDAN)
//                .fuel(FuelType.GASOLINE)
//                .transmission(TransmissionType.AT)
//                .drive(DriveType.AWD)
//                .enginePower(625)
//                .engineVolume(4.4)
//                .price(new BigDecimal(9500000))
//                .componentTypes(compSet)
//                .build();
//        var model = carModelRepository.save(bmw);
//
//        var componentRequest = new ComponentRequest(
//                List.of(model.getId()),
//                componentType,
//                "TEST",
//                new BigDecimal(100000));
//        ComponentDto response = webTestClient.post()
//                .uri("/components")
//                .bodyValue(componentRequest)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(ComponentDto.class)
//                .returnResult()
//                .getResponseBody();
//
//        Assertions.assertNotNull(response);
//        Assertions.assertNotNull(response.id());
//        Assertions.assertNotNull(response.name());
//    }
}
