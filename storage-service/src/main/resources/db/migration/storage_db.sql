--liquibase formatted sql


--changeset openai:001-create-replaceable-component-type
CREATE TABLE replaceable_component_type (
    replaceable_component_id UUID PRIMARY KEY,
    removed BOOLEAN,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    replaceable_component_type VARCHAR(64) NOT NULL,
    price NUMERIC(38, 10),
    reserved_by UUID
);

--changeset openai:001b-create-replaceable-compatible-cars
CREATE TABLE replaceable_component_type_compatible_cars (
    replaceable_component_type_id UUID NOT NULL,
    compatible_cars UUID NOT NULL,
    removed BOOLEAN,
    PRIMARY KEY (replaceable_component_type_id, compatible_cars),
    CONSTRAINT fk_replaceable_component_type_compatible_cars_owner
    FOREIGN KEY (replaceable_component_type_id) REFERENCES replaceable_component_type(replaceable_component_id) ON DELETE CASCADE
);

--changeset openai:002-create-component-types
CREATE TABLE component_types (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    removed BOOLEAN,
    irreplaceable_component_type VARCHAR(64) NOT NULL
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
    removed BOOLEAN,
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
    removed BOOLEAN,
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
    removed BOOLEAN,
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

--changeset openai:009-create-warehouse-acceptance
CREATE TABLE warehouse_acceptance (
      id UUID PRIMARY KEY,
      created_at TIMESTAMP WITH TIME ZONE NOT NULL,
      updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
      car_order_id UUID,
      state VARCHAR(255) NOT NULL,
      removed BOOLEAN
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

--changeset openai:012-create-outbox-messages
CREATE TABLE outbox_messages (
     outbox_message_id UUID PRIMARY KEY,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
     event_type VARCHAR(255) NOT NULL,
     payload TEXT NOT NULL,
     processed BOOLEAN NOT NULL DEFAULT FALSE,
     removed BOOLEAN DEFAULT FALSE,
     stat VARCHAR(255) NOT NULL,
     car_order_id UUID NOT NULL,
     attempts INTEGER NOT NULL DEFAULT 0,
     trace_id VARCHAR(64),
     span_id VARCHAR(64)
);

--changeset burunsuzian:013-extend-warehouse-acceptance
ALTER TABLE warehouse_acceptance ADD COLUMN order_type VARCHAR(64) NOT NULL DEFAULT 'REGULAR';
ALTER TABLE warehouse_acceptance ADD COLUMN car_id UUID;
ALTER TABLE warehouse_acceptance ADD COLUMN car_model_id UUID;
ALTER TABLE warehouse_acceptance ADD COLUMN responsible_staff_id UUID;

CREATE TABLE warehouse_acceptance_components (
     acceptance_id UUID NOT NULL,
     component_id UUID NOT NULL,
     PRIMARY KEY (acceptance_id, component_id),
     CONSTRAINT fk_wa_components_acceptance
         FOREIGN KEY (acceptance_id) REFERENCES warehouse_acceptance(id) ON DELETE CASCADE,
     CONSTRAINT fk_wa_components_component
         FOREIGN KEY (component_id) REFERENCES replaceable_component_type(replaceable_component_id) ON DELETE CASCADE
);