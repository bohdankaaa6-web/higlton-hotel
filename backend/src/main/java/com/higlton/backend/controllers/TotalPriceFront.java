@Test
void createBooking_SingleDayStay_CalculatesAsOneDay() throws Exception {
    Room room = new Room();
    room.setId(1L);
    room.setPricePerNight(100.0);

    Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
    Mockito.when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

    // Заїзд і виїзд в один і той же день (2023-10-10)
    String json = "{\"room\": {\"id\": 1}, \"checkIn\": \"2023-10-10\", \"checkOut\": \"2023-10-10\"}";

    mockMvc.perform(post("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPrice").value(100.0)); // Має бути ціна за 1 добу
}