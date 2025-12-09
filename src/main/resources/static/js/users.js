$(document).ready(function() {
    $('#usersTable').DataTable({
        pageLength: 10,
        order: [[0, 'asc']]
    });
});

function addStaff() {
    const modal = new bootstrap.Modal(document.getElementById('addStaffModal'));
    modal.show();
}

function editUser(button) {
    const id = button.getAttribute('data-id');
    const fname = button.getAttribute('data-fname');
    const lname = button.getAttribute('data-lname');
    const username = button.getAttribute('data-username');
    const email = button.getAttribute('data-email');
    const password = button.getAttribute('data-password');
    
    document.getElementById('userId').value = id;
    document.getElementById('fname').value = fname;
    document.getElementById('lname').value = lname;
    document.getElementById('username').value = username;
    document.getElementById('email').value = email;
    document.getElementById('password').value = password;
    
    document.getElementById('editUserForm').action = '/update-user/' + id;
    
    const modal = new bootstrap.Modal(document.getElementById('editUserModal'));
    modal.show();
}
