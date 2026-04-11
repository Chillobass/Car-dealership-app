--liquibase formatted sql

--changeset openai:001-create-replaceable-component-type
CREATE TABLE replaceable_component_type (
    replaceable_component_id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    replaceable_component_type VARCHAR(64) NOT NULL,
    price NUMERIC(38, 10)
);

--changeset openai:001b-create-replaceable-compatible-cars
CREATE TABLE replaceable_component_type_compatible_cars (
    replaceable_component_type_id UUID NOT NULL,
    compatible_cars UUID NOT NULL,
    PRIMARY KEY (replaceable_component_type_id, compatible_cars),
    CONSTRAINT fk_replaceable_component_type_compatible_cars_owner
        FOREIGN KEY (replaceable_component_type_id) REFERENCES replaceable_component_type(replaceable_component_id) ON DELETE CASCADE
);

--changeset openai:002-create-component-types
CREATE TABLE component_types (
     id UUID PRIMARY KEY,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
     irreplaceable_component_type VARCHAR(64) NOT NULL
);

--changeset openai:003-create-order-state
CREATE TABLE order_state (
     id UUID PRIMARY KEY,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
     order_state VARCHAR(64) NOT NULL,
     state VARCHAR(255) NOT NULL
);

--changeset openai:004-create-users
CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    user_type VARCHAR(255)
);

--changeset openai:005-create-car-models
CREATE TABLE car_models (
    model_id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    brand VARCHAR(255),
    base_price NUMERIC(38, 10),
    car_color VARCHAR(255),
    car_drive UUID,
    car_body UUID,
    fuel UUID,
    gear_box UUID,
    engine_power DOUBLE PRECISION,
    engine_capacity DOUBLE PRECISION,
    CONSTRAINT fk_car_models_car_drive
        FOREIGN KEY (car_drive) REFERENCES component_types(id),
    CONSTRAINT fk_car_models_car_body
        FOREIGN KEY (car_body) REFERENCES component_types(id),
    CONSTRAINT fk_car_models_fuel
        FOREIGN KEY (fuel) REFERENCES component_types(id),
    CONSTRAINT fk_car_models_gear_box
        FOREIGN KEY (gear_box) REFERENCES component_types(id)
);

--changeset openai:006-create-cars
CREATE TABLE cars (
      car_id UUID PRIMARY KEY,
      created_at TIMESTAMP WITH TIME ZONE NOT NULL,
      updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
      model_name UUID,
      steering_wheel_id UUID,
      wheels_id UUID,
      transmission_id UUID,
      decor_id UUID,
      CONSTRAINT fk_cars_model_name
          FOREIGN KEY (model_name) REFERENCES car_models(model_id),
      CONSTRAINT fk_cars_steering_wheel
          FOREIGN KEY (steering_wheel_id) REFERENCES replaceable_component_type(replaceable_component_id),
      CONSTRAINT fk_cars_wheels
          FOREIGN KEY (wheels_id) REFERENCES replaceable_component_type(replaceable_component_id),
      CONSTRAINT fk_cars_transmission
          FOREIGN KEY (transmission_id) REFERENCES replaceable_component_type(replaceable_component_id),
      CONSTRAINT fk_cars_decor
          FOREIGN KEY (decor_id) REFERENCES replaceable_component_type(replaceable_component_id)
);

--changeset openai:007-create-detail
CREATE TABLE detail (
    detail_id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    category UUID,
    price NUMERIC(38, 10),
    CONSTRAINT fk_detail_category
        FOREIGN KEY (category) REFERENCES replaceable_component_type(replaceable_component_id)
);

--changeset openai:008-create-detail-compatible-models
CREATE TABLE detail_compatible_models (
    detail_id UUID NOT NULL,
    model_id UUID NOT NULL,
    PRIMARY KEY (detail_id, model_id),
    CONSTRAINT fk_detail_compatible_models_detail
        FOREIGN KEY (detail_id) REFERENCES detail(detail_id) ON DELETE CASCADE,
    CONSTRAINT fk_detail_compatible_models_model
        FOREIGN KEY (model_id) REFERENCES car_models(model_id) ON DELETE CASCADE
);

--changeset openai:009-create-car-order
CREATE TABLE car_order (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_id UUID,
    manager_id UUID,
    ordered_car_id UUID,
    order_state UUID,
    CONSTRAINT fk_car_order_customer
        FOREIGN KEY (customer_id) REFERENCES users(user_id),
    CONSTRAINT fk_car_order_manager
        FOREIGN KEY (manager_id) REFERENCES users(user_id),
    CONSTRAINT fk_car_order_ordered_car
        FOREIGN KEY (ordered_car_id) REFERENCES cars(car_id),
    CONSTRAINT fk_car_order_state
        FOREIGN KEY (order_state) REFERENCES order_state(id)
);

--changeset openai:010-create-test-drive-request
CREATE TABLE test_drive_request (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_id UUID,
    ordered_car_id UUID,
    time TIMESTAMP WITH TIME ZONE,
    finished BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_test_drive_request_customer
    FOREIGN KEY (customer_id) REFERENCES users(user_id),
    CONSTRAINT fk_test_drive_request_car
    FOREIGN KEY (ordered_car_id) REFERENCES cars(car_id)
);

--changeset openai:011-create-indexes
CREATE INDEX idx_car_models_car_drive ON car_models(car_drive);
CREATE INDEX idx_car_models_car_body ON car_models(car_body);
CREATE INDEX idx_car_models_fuel ON car_models(fuel);
CREATE INDEX idx_car_models_gear_box ON car_models(gear_box);
CREATE INDEX idx_cars_model_name ON cars(model_name);
CREATE INDEX idx_cars_steering_wheel ON cars(steering_wheel_id);
CREATE INDEX idx_cars_wheels ON cars(wheels_id);
CREATE INDEX idx_cars_transmission ON cars(transmission_id);
CREATE INDEX idx_cars_decor ON cars(decor_id);
CREATE INDEX idx_detail_category ON detail(category);
CREATE INDEX idx_car_order_customer_id ON car_order(customer_id);
CREATE INDEX idx_car_order_manager_id ON car_order(manager_id);
CREATE INDEX idx_car_order_ordered_car_id ON car_order(ordered_car_id);
CREATE INDEX idx_car_order_order_state ON car_order(order_state);
CREATE INDEX idx_test_drive_request_customer_id ON test_drive_request(customer_id);
CREATE INDEX idx_test_drive_request_ordered_car_id ON test_drive_request(ordered_car_id);
