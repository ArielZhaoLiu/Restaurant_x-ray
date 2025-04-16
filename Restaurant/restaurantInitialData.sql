INSERT INTO Layout (name, description, created_date, last_updated_date, archived) VALUES
('Romantic Night', 'Candlelight dinner setup', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
('Classic White', 'Elegant white-themed arrangement', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
('Tropical Vibe', 'Palm leaves and beach theme', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
('Rustic Wood', 'Wooden furniture and string lights', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
('Garden Fresh', 'Greenery and floral patterns', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
('Futuristic', 'Modern minimalist with LEDs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE),
('Vintage', 'Old-school charm with antique touch', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE);

INSERT INTO dining_table (number_of_seats, archived, layout_id) VALUES
(2, FALSE, 1),
(4, FALSE, 1),
(6, FALSE, 1),
(4, FALSE, 1),

(2, FALSE, 2),
(4, FALSE, 2),
(6, FALSE, 2),
(4, FALSE, 2),

(2, FALSE, 3),
(4, FALSE, 3),
(6, FALSE, 3),
(4, FALSE, 3),

(2, FALSE, 4),
(4, FALSE, 4),
(6, FALSE, 4),
(4, FALSE, 4),

(2, FALSE, 5),
(4, FALSE, 5),
(6, FALSE, 5),
(4, FALSE, 5),

(2, FALSE, 6),
(4, FALSE, 6),
(6, FALSE, 6),
(4, FALSE, 6),

(2, FALSE, 7),
(4, FALSE, 7),
(6, FALSE, 7),
(4, FALSE, 7);

-- Inserting into the 'menu' table
INSERT INTO menu (name, description, created_date_time) VALUES
('Breakfast Menu', 'Start your day with a selection of breakfast favorites.', CURRENT_TIMESTAMP),
('Lunch Menu', 'A variety of light and filling options for lunch.', CURRENT_TIMESTAMP),
('Dinner Menu', 'Indulge in hearty meals perfect for dinner.', CURRENT_TIMESTAMP),
('Snack Menu', 'Quick bites for when you need a small snack.', CURRENT_TIMESTAMP),
('Beverage Menu', 'Refreshing drinks to complement your meal.', CURRENT_TIMESTAMP),
('Dessert Menu', 'Sweet treats to satisfy your cravings.', CURRENT_TIMESTAMP),
('Special Menu', 'Exclusive dishes for a special occasion.', CURRENT_TIMESTAMP);

-- Inserting into the 'menu_item' table
-- Breakfast Menu (menu_id = 1)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Pancakes', 'Fluffy pancakes served with syrup and butter.', 1, CURRENT_TIMESTAMP),
('Scrambled Eggs', 'Soft scrambled eggs, perfect with toast.', 1, CURRENT_TIMESTAMP),
('French Toast', 'Golden brown French toast with powdered sugar.', 1, CURRENT_TIMESTAMP),
('Omelette', 'A classic omelette with your choice of fillings.', 1, CURRENT_TIMESTAMP),
('Waffles', 'Crispy waffles with a hint of vanilla.', 1, CURRENT_TIMESTAMP),
('Bacon', 'Crispy fried bacon strips.', 1, CURRENT_TIMESTAMP),
('Hash Browns', 'Crispy and golden brown hash browns.', 1, CURRENT_TIMESTAMP);

-- Lunch Menu (menu_id = 2)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Burger', 'Juicy beef burger with lettuce, tomato, and cheese.', 2, CURRENT_TIMESTAMP),
('Grilled Chicken Sandwich', 'Tender grilled chicken with your choice of sauce.', 2, CURRENT_TIMESTAMP),
('Caesar Salad', 'Crispy romaine lettuce with Caesar dressing and croutons.', 2, CURRENT_TIMESTAMP),
('Pasta Salad', 'A refreshing pasta salad with a tangy vinaigrette.', 2, CURRENT_TIMESTAMP),
('Club Sandwich', 'Classic triple-decker sandwich with turkey, ham, and cheese.', 2, CURRENT_TIMESTAMP),
('Fries', 'Crispy golden fries, perfect for dipping.', 2, CURRENT_TIMESTAMP),
('Soup of the Day', 'Freshly made soup, ask for today’s special.', 2, CURRENT_TIMESTAMP);

-- Dinner Menu (menu_id = 3)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Steak', 'Grilled to perfection with your choice of sides.', 3, CURRENT_TIMESTAMP),
('Salmon', 'Oven-baked salmon with a lemon dill sauce.', 3, CURRENT_TIMESTAMP),
('Grilled Chicken', 'Tender grilled chicken served with vegetables.', 3, CURRENT_TIMESTAMP),
('Vegetable Stir Fry', 'Stir-fried vegetables with a savory sauce.', 3, CURRENT_TIMESTAMP),
('Lasagna', 'Layers of pasta, meat sauce, and cheese, baked to perfection.', 3, CURRENT_TIMESTAMP),
('Mashed Potatoes', 'Creamy mashed potatoes with butter and gravy.', 3, CURRENT_TIMESTAMP),
('Steamed Vegetables', 'Seasoned and lightly steamed vegetables.', 3, CURRENT_TIMESTAMP);

-- Snack Menu (menu_id = 4)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Chips', 'A bag of crispy potato chips.', 4, CURRENT_TIMESTAMP),
('Popcorn', 'Fluffy popcorn, perfect for munching.', 4, CURRENT_TIMESTAMP),
('Fruit Cup', 'Fresh seasonal fruits cut and served in a cup.', 4, CURRENT_TIMESTAMP),
('Granola Bar', 'Crunchy granola bar with a mix of nuts and honey.', 4, CURRENT_TIMESTAMP),
('Pretzels', 'Warm, salted pretzels.', 4, CURRENT_TIMESTAMP),
('Cheese Sticks', 'Fried cheese sticks served with marinara sauce.', 4, CURRENT_TIMESTAMP),
('Vegetable Platter', 'A platter of fresh, crunchy vegetables served with dip.', 4, CURRENT_TIMESTAMP);

-- Beverage Menu (menu_id = 5)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Coffee', 'Hot brewed coffee, a perfect start to your day.', 5, CURRENT_TIMESTAMP),
('Tea', 'Assorted teas, served hot or iced.', 5, CURRENT_TIMESTAMP),
('Orange Juice', 'Freshly squeezed orange juice.', 5, CURRENT_TIMESTAMP),
('Lemonade', 'A refreshing cold lemonade.', 5, CURRENT_TIMESTAMP),
('Soda', 'Carbonated soft drinks in various flavors.', 5, CURRENT_TIMESTAMP),
('Milk', 'Cold milk served in a glass or with cookies.', 5, CURRENT_TIMESTAMP),
('Iced Tea', 'Chilled tea served with lemon and ice.', 5, CURRENT_TIMESTAMP);

-- Dessert Menu (menu_id = 6)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Chocolate Cake', 'Rich and moist chocolate cake with a creamy frosting.', 6, CURRENT_TIMESTAMP),
('Cheesecake', 'Smooth and creamy cheesecake with a graham cracker crust.', 6, CURRENT_TIMESTAMP),
('Ice Cream', 'Your choice of vanilla, chocolate, or strawberry ice cream.', 6, CURRENT_TIMESTAMP),
('Fruit Tart', 'Fresh fruit arranged on a buttery tart crust.', 6, CURRENT_TIMESTAMP),
('Brownies', 'Soft and fudgy brownies with chocolate chips.', 6, CURRENT_TIMESTAMP),
('Apple Pie', 'Classic apple pie with a buttery crust.', 6, CURRENT_TIMESTAMP),
('Cupcakes', 'Delicious cupcakes topped with frosting and sprinkles.', 6, CURRENT_TIMESTAMP);

-- Special Menu (menu_id = 7)
INSERT INTO menu_item (name, description, menu_id, created_date_time) VALUES
('Lobster Tail', 'Grilled lobster tail with garlic butter sauce.', 7, CURRENT_TIMESTAMP),
('Filet Mignon', 'Tender filet mignon, cooked to your liking.', 7, CURRENT_TIMESTAMP),
('Truffle Fries', 'Crispy fries tossed in truffle oil and parmesan.', 7, CURRENT_TIMESTAMP),
('Caviar', 'Premium black caviar served with blinis and crème fraîche.', 7, CURRENT_TIMESTAMP),
('Foie Gras', 'Seared foie gras served with a rich sauce.', 7, CURRENT_TIMESTAMP),
('Shrimp Cocktail', 'Chilled shrimp served with a tangy cocktail sauce.', 7, CURRENT_TIMESTAMP),
('Crème Brûlée', 'Silky smooth vanilla custard topped with caramelized sugar.', 7, CURRENT_TIMESTAMP);

-- Inserting into the 'event' table (5 events with different durations and is_archived field)
-- Event 1 (One-day event)
INSERT INTO event (name, description, start_date, end_date, price, layout_id, menu_id, created_date, updated_at, is_archived) VALUES
('Spring Festival', 'Celebrate the arrival of spring with food, music, and fun activities.', '2025-05-01', '2025-05-01', 30.00, 1, 1, CURRENT_DATE, CURRENT_TIMESTAMP, FALSE),
-- Event 2 (Multiple-day event)
('Food and Wine Expo', 'A 3-day event featuring tastings and cooking demonstrations.', '2025-06-10', '2025-06-12', 50.00, 2, 2, CURRENT_DATE, CURRENT_TIMESTAMP, FALSE),
-- Event 3 (One-day event)
('New Year’s Eve Party', 'A night of celebration to ring in the New Year.', '2025-12-31', '2025-12-31', 100.00, 3, 3, CURRENT_DATE, CURRENT_TIMESTAMP, FALSE),
-- Event 4 (Multiple-day event)
('Summer Music Festival', 'A 5-day festival featuring live music from various artists.', '2025-07-01', '2025-07-05', 75.00, 4, 4, CURRENT_DATE, CURRENT_TIMESTAMP, FALSE),
-- Event 5 (One-day event)
('Christmas Dinner', 'A festive dinner event with a special holiday menu.', '2025-12-25', '2025-12-25', 40.00, 5, 5, CURRENT_DATE, CURRENT_TIMESTAMP, FALSE);

-- Inserting into the 'seating' table for each event (5 seatings per event)
-- Spring Festival (event_id = 1)
INSERT INTO seating (seating_date_time, seating_duration, event_id, created_date_time, updated_at) VALUES
('2025-05-01 10:00', 120, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- 2 hours (120 minutes)
('2025-05-01 12:00', 120, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-05-01 14:00', 120, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-05-01 16:00', 120, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-05-01 18:00', 120, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Food and Wine Expo (event_id = 2)
INSERT INTO seating (seating_date_time, seating_duration, event_id, created_date_time, updated_at) VALUES
('2025-06-10 10:00', 180, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- 3 hours (180 minutes)
('2025-06-10 13:00', 180, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-06-10 16:00', 180, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-06-11 10:00', 180, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-06-11 13:00', 180, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- New Year’s Eve Party (event_id = 3)
INSERT INTO seating (seating_date_time, seating_duration, event_id, created_date_time, updated_at) VALUES
('2025-12-31 18:00', 240, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- 4 hours (240 minutes)
('2025-12-31 22:00', 240, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-12-31 00:00', 240, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-12-31 02:00', 240, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-12-31 04:00', 240, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Summer Music Festival (event_id = 4)
INSERT INTO seating (seating_date_time, seating_duration, event_id, created_date_time, updated_at) VALUES
('2025-07-01 10:00', 240, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- 4 hours (240 minutes)
('2025-07-01 14:00', 240, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-07-02 10:00', 240, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-07-02 14:00', 240, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-07-03 10:00', 240, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Christmas Dinner (event_id = 5)
INSERT INTO seating (seating_date_time, seating_duration, event_id, created_date_time, updated_at) VALUES
('2025-12-25 18:00', 180, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  -- 3 hours (180 minutes)
('2025-12-25 19:00', 180, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-12-25 20:00', 180, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-12-25 21:00', 180, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2025-12-25 22:00', 180, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Reservations
INSERT INTO reservation_request (email, first_name, last_name, group_size, seating_id, table_id, status) VALUES
('alice.johnson@example.com', 'Alice', 'Johnson', 4, 1, null, 0),
('bob.smith@example.com', 'Bob', 'Smith', 2, 2, 2, 1),
('david.brown@example.com', 'David', 'Brown', 3, 1, null, 0),
('eva.davis@example.com', 'Eva', 'Davis', 5, 2, null, 0);