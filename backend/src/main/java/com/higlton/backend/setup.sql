TRUNCATE TABLE booking, review, room, mood, client, hotel CASCADE;

INSERT INTO hotel (id, name, city, country, address) VALUES 
(1, 'Higlton Kyiv', 'Kyiv', 'Ukraine', 'Khreshchatyk St, 1'),
(2, 'Higlton New York', 'New York', 'USA', '5th Avenue, 100');

INSERT INTO mood (id, name, description) VALUES 
(1, 'Luxury', 'Premium experience'), (2, 'Business', 'Work-friendly');

INSERT INTO room (id, hotel_id, mood_id, title, price_per_night, capacity, bed_type, area_m2, image_url) VALUES 
(1, 1, 1, 'Presidential Suite', 500.0, 2, 'King Size Bed', 120.0, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800'),
(2, 1, 2, 'Classic Single Room', 120.0, 1, 'Single Bed', 25.0, 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=800'),
(3, 1, 1, 'Deluxe Double Room', 250.0, 2, 'King Size Bed', 45.0, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a?w=800'),
(4, 1, 2, 'Executive Suite', 350.0, 2, 'Queen Size Bed', 65.0, 'https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=800'),
(5, 1, 1, 'Family Apartment', 400.0, 4, '1 King, 2 Single', 80.0, 'https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?w=800'),
(6, 1, 1, 'Luxury Penthouse', 750.0, 3, 'Super King Bed', 150.0, 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=800');

INSERT INTO client (id, first_name, last_name, email, phone, password) VALUES 
(1, 'Ivan', 'Ivanov', 'ivan@gmail.com', '+380501234567', 'password123');

SELECT setval('hotel_id_seq', (SELECT MAX(id) FROM hotel));
SELECT setval('mood_id_seq', (SELECT MAX(id) FROM mood));
SELECT setval('room_id_seq', (SELECT MAX(id) FROM room));
SELECT setval('client_id_seq', (SELECT MAX(id) FROM client));