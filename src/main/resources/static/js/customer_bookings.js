$(document).ready(function() {
    if ($('#bookingsTable').length) {
        $('#bookingsTable').DataTable({
            pageLength: 10,
            order: [[0, 'desc']],
            language: {
                search: "Search bookings:",
                lengthMenu: "Show _MENU_ bookings per page",
                info: "Showing _START_ to _END_ of _TOTAL_ bookings"
            }
        });
    }
});
