INSERT INTO services (id, slug, title, summary, description, authority_name, status, valid_from, valid_to)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'business-license', 'Small Business License',
   'Apply for a license to operate a small business within the city.',
   'This service registers a small business with the city government and issues a license number for compliance.',
   'City Department of Commerce', 'ACTIVE', '2023-01-01', NULL),
  ('22222222-2222-2222-2222-222222222222', 'building-permit', 'Residential Building Permit',
   'Get approval before starting residential construction or renovations.',
   'Covers structural modifications, safety inspections, and compliance with zoning rules.',
   'City Planning Office', 'ACTIVE', '2022-06-01', NULL),
  ('33333333-3333-3333-3333-333333333333', 'event-permit', 'Public Event Permit',
   'Request permission to host a public event in city spaces.',
   'Includes permits for street closures, amplified sound, and crowd management.',
   'City Events Administration', 'DEPRECATED', '2020-01-01', '2024-01-31');

INSERT INTO requirements (id, service_id, title, details, required, order_index)
VALUES
  ('aaaaaaa1-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 'Business registration',
   'Provide proof of business registration with the state.', TRUE, 1),
  ('aaaaaaa1-0000-0000-0000-000000000002', '11111111-1111-1111-1111-111111111111', 'Zoning confirmation',
   'Confirm the business location complies with zoning rules.', TRUE, 2),
  ('aaaaaaa2-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 'Site plan',
   'Submit a detailed site plan with measurements.', TRUE, 1),
  ('aaaaaaa3-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 'Event safety plan',
   'Provide emergency and safety procedures for attendees.', TRUE, 1);

INSERT INTO documents (id, service_id, name, description, url)
VALUES
  ('bbbbbbb1-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 'License application form',
   'Standard business license application form.', 'https://example.org/forms/business-license.pdf'),
  ('bbbbbbb2-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 'Permit checklist',
   'Checklist for required documents.', 'https://example.org/forms/building-permit-checklist.pdf'),
  ('bbbbbbb3-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 'Event permit request',
   'Submit this form to request a permit.', 'https://example.org/forms/event-permit.pdf');

INSERT INTO fees (id, service_id, amount, currency, notes)
VALUES
  ('ccccccc1-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 150.00, 'USD', 'Annual license fee.'),
  ('ccccccc2-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 350.00, 'USD', 'Base permit fee.'),
  ('ccccccc3-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 75.00, 'USD', 'Event inspection fee.');

INSERT INTO timeframes (id, service_id, min_days, max_days, notes)
VALUES
  ('ddddddd1-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 5, 10, 'Standard processing time.'),
  ('ddddddd2-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 10, 30, 'Plan review may extend processing.'),
  ('ddddddd3-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 3, 14, 'Timeframe depends on venue capacity.');

INSERT INTO legal_sources (id, service_id, title, url, published_date)
VALUES
  ('eeeeeee1-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 'City Code Section 10.2',
   'https://example.org/legal/city-code-10-2', '2022-02-01'),
  ('eeeeeee2-0000-0000-0000-000000000001', '22222222-2222-2222-2222-222222222222', 'Building Safety Ordinance 5A',
   'https://example.org/legal/building-ordinance-5a', '2021-05-15'),
  ('eeeeeee3-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 'Public Events Act',
   'https://example.org/legal/public-events-act', '2019-09-10');
