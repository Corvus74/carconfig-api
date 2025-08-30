package com.computacenter.carconfig.repository.order;

import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.base.CarColor;
import com.computacenter.carconfig.entities.base.CarEngine;
import com.computacenter.carconfig.entities.base.CarRim;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import com.computacenter.carconfig.entities.order.*;
import com.computacenter.carconfig.enums.FuelType;
import com.computacenter.carconfig.enums.MaterialType;
import com.computacenter.carconfig.enums.OrderStatusEnum;
import com.computacenter.carconfig.enums.PaintingType;
import com.computacenter.carconfig.repository.OrderUserRepository;
import com.computacenter.carconfig.repository.pool.CarColorRepository;
import com.computacenter.carconfig.repository.pool.CarEngineRepository;
import com.computacenter.carconfig.repository.pool.CarRimRepository;
import com.computacenter.carconfig.repository.pool.SpecialEquipmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CarOrderDataJpaTest {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private CarColorOrderRepository carColorOrderRepository;

    @Autowired
    private CarEngineOrderRepository carEngineOrderRepository;

    @Autowired
    private CarRimOrderRepository carRimOrderRepository;

    @Autowired
    private SpecialEquipmentOrderRepository specialEquipmentOrderRepository;

    @Autowired
    private SpecialEquipmentRepository specialEquipmentRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderUserRepository orderUserRepository;

    @Autowired
    private CarColorRepository carColorRepository;

    @Autowired
    private CarEngineRepository carEngineRepository;

    @Autowired
    private CarRimRepository carRimRepository;

    @Test
    void saveAndFindCarOrderByPublicId_shouldPersistGraphAndRetrieveIt() {
        // Create base items
        CarColor color = new CarColor();
        color.setOrderNumber("C-001");
        color.setColorName("Red");
        color.setProductId("PID-COLOR-1");
        color.setMaterialType(MaterialType.MATTE);
        color.setPaintingType(PaintingType.BASE);
        color.setPrice(1500);

        CarEngine engine = new CarEngine();
        engine.setOrderNumber("E-001");
        engine.setDescription("2.0T");
        engine.setFuelType(FuelType.GASOLINE);
        engine.setEngineType("Turbo");
        engine.setProductId("PID-ENGINE-1");
        engine.setModel("X20");
        engine.setPrice(5500);

        CarRim rim = new CarRim();
        rim.setOrderNumber("R-001");
        rim.setRimName("SportRim");
        rim.setModel("SR-20");
        // innerDiameter left null intentionally to avoid Size validator on Integer
        rim.setProductId("PID-RIM-1");
        rim.setPrice(1200);

        // Persist base items that need to exist before orders when cascade is not present
        color = carColorRepository.save(color);
        engine = carEngineRepository.save(engine);
        rim = carRimRepository.save(rim);

        SpecialEquipment se = new SpecialEquipment();
        se.setId(1L);
        se.setOrderNumber("SE-001");
        se.setEquipmentName("Panorama roof");
        se.setProductId("PID-SE-1");
        se.setPrice(900);
        se = specialEquipmentRepository.save(se); // ManyToOne in SpecialEquipmentOrder has no cascade

        // Create order statuses
        OrderStatus orderStatusMain = new OrderStatus();
        orderStatusMain.setCurrentStatus(OrderStatusEnum.RECEIVED);
        orderStatusMain = orderStatusRepository.save(orderStatusMain);

        OrderStatus orderStatusColor = new OrderStatus();
        orderStatusColor.setCurrentStatus(OrderStatusEnum.RECEIVED);
        orderStatusColor = orderStatusRepository.save(orderStatusColor);

        OrderStatus orderStatusEngine = new OrderStatus();
        orderStatusEngine.setCurrentStatus(OrderStatusEnum.RECEIVED);
        orderStatusEngine = orderStatusRepository.save(orderStatusEngine);

        OrderStatus orderStatusRim = new OrderStatus();
        orderStatusRim.setCurrentStatus(OrderStatusEnum.RECEIVED);
        orderStatusRim = orderStatusRepository.save(orderStatusRim);

        OrderStatus orderStatusSe = new OrderStatus();
        orderStatusSe.setCurrentStatus(OrderStatusEnum.RECEIVED);
        orderStatusSe = orderStatusRepository.save(orderStatusSe);

        // Create sub-orders
        CarColorOrder colorOrder = new CarColorOrder();
        colorOrder.setCarColorOrderId("CO-" + UUID.randomUUID());
        colorOrder.setCarColor(color);
        colorOrder.setOrderStatus(orderStatusColor);
        colorOrder = carColorOrderRepository.save(colorOrder);

        CarEngineOrder engineOrder = new CarEngineOrder();
        engineOrder.setCarEngineOrderId("EN-" + UUID.randomUUID());
        engineOrder.setCarEngine(engine);
        engineOrder.setOrderStatus(orderStatusEngine);
        engineOrder = carEngineOrderRepository.save(engineOrder);

        CarRimOrder rimOrder = new CarRimOrder();
        rimOrder.setCarRimOrderId("RI-" + UUID.randomUUID());
        rimOrder.setCarRim(rim);
        rimOrder.setOrderStatus(orderStatusRim);
        rimOrder = carRimOrderRepository.save(rimOrder);

        SpecialEquipmentOrder seOrder = new SpecialEquipmentOrder();
        seOrder.setSpecialEquipmentOrderId("SE-" + UUID.randomUUID());
        seOrder.setSpecialEquipment(se);
        seOrder.setOrderStatus(orderStatusSe);
        seOrder = specialEquipmentOrderRepository.save(seOrder);

        // User
        OrderUser user = new OrderUser();
        user.setUserId("U-1");
        user.setEmail("john.doe@example.com");
        user.setUserName("john");
        user.setValid(true);
        user = orderUserRepository.save(user);

        // Car order aggregate
        CarOrder order = new CarOrder();
        String publicOrderId = "ORD-" + UUID.randomUUID();
        order.setCarOrderId(publicOrderId);
        order.setCarColorOrder(colorOrder);
        order.setCarEngineOrder(engineOrder);
        order.setCarRimOrder(rimOrder);
        order.setSpecialEquipmentOrders(List.of(seOrder));
        order.setOrderStatus(orderStatusMain);
        order.setOrderUser(user);
        order.setTotalPrice(1500 + 5500 + 1200 + 900);

        CarOrder saved = orderRepository.save(order);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCarOrderId()).isEqualTo(publicOrderId);
        assertThat(saved.getOrderUser()).isNotNull();
        assertThat(saved.getCarColorOrder()).isNotNull();
        assertThat(saved.getCarEngineOrder()).isNotNull();
        assertThat(saved.getCarRimOrder()).isNotNull();
        assertThat(saved.getSpecialEquipmentOrders()).hasSize(1);

        Optional<CarOrder> reloadedOpt = orderRepository.findByCarOrderId(publicOrderId);
        assertThat(reloadedOpt).isPresent();
        CarOrder reloaded = reloadedOpt.get();
        assertThat(reloaded.getOrderUser().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(reloaded.getCarColorOrder().getCarColor().getProductId()).isEqualTo("PID-COLOR-1");
        assertThat(reloaded.getCarEngineOrder().getCarEngine().getProductId()).isEqualTo("PID-ENGINE-1");
        assertThat(reloaded.getCarRimOrder().getCarRim().getProductId()).isEqualTo("PID-RIM-1");
        assertThat(reloaded.getSpecialEquipmentOrders()).hasSize(1);
        assertThat(reloaded.getSpecialEquipmentOrders().getFirst().getSpecialEquipment().getProductId()).isEqualTo("PID-SE-1");
    }
}