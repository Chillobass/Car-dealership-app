--liquibase formatted sql

--changeset openai:002-create-outbox-messages
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

--changeset openai:003-create-order-state
CREATE TABLE order_state (
     id UUID PRIMARY KEY,
     created_at TIMESTAMP WITH TIME ZONE NOT NULL,
     updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
     order_state VARCHAR(64) NOT NULL,
     state VARCHAR(255) NOT NULL,
     removed BOOLEAN
);

--changeset openai:009-create-car-order
CREATE TABLE car_order (
    car_order_id UUID PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_id UUID,
    manager_id UUID,
    ordered_car_id UUID,
    order_state UUID,
    is_custom_car BOOLEAN NOT NULL,
    removed BOOLEAN,
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
    removed BOOLEAN,
    finished BOOLEAN NOT NULL DEFAULT FALSE
);

--changeset openai:011-create-indexes
CREATE INDEX idx_car_order_customer_id ON car_order(customer_id);
CREATE INDEX idx_car_order_manager_id ON car_order(manager_id);
CREATE INDEX idx_car_order_ordered_car_id ON car_order(ordered_car_id);
CREATE INDEX idx_car_order_order_state ON car_order(order_state);
CREATE INDEX idx_test_drive_request_customer_id ON test_drive_request(customer_id);
CREATE INDEX idx_test_drive_request_ordered_car_id ON test_drive_request(ordered_car_id);


