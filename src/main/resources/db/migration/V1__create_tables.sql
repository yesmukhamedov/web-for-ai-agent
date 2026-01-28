CREATE TABLE services (
  id UUID PRIMARY KEY,
  slug VARCHAR(150) NOT NULL UNIQUE,
  title VARCHAR(255) NOT NULL,
  summary VARCHAR(500) NOT NULL,
  description VARCHAR(4000) NOT NULL,
  authority_name VARCHAR(255) NOT NULL,
  status VARCHAR(20) NOT NULL,
  valid_from DATE,
  valid_to DATE
);

CREATE TABLE requirements (
  id UUID PRIMARY KEY,
  service_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  details VARCHAR(2000) NOT NULL,
  required BOOLEAN NOT NULL,
  order_index INT NOT NULL,
  CONSTRAINT fk_requirements_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE documents (
  id UUID PRIMARY KEY,
  service_id UUID NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  url VARCHAR(500) NOT NULL,
  CONSTRAINT fk_documents_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE fees (
  id UUID PRIMARY KEY,
  service_id UUID NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  notes VARCHAR(2000),
  CONSTRAINT fk_fees_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE timeframes (
  id UUID PRIMARY KEY,
  service_id UUID NOT NULL,
  min_days INT NOT NULL,
  max_days INT NOT NULL,
  notes VARCHAR(2000),
  CONSTRAINT fk_timeframes_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE legal_sources (
  id UUID PRIMARY KEY,
  service_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  url VARCHAR(500) NOT NULL,
  published_date DATE,
  CONSTRAINT fk_legal_sources_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);
