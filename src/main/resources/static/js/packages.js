$(document).ready(function() {
    $('#packagesTable').DataTable({
        pageLength: 10,
        order: [[0, 'asc']]
    });
});

function addPackage() {
    document.getElementById('packageModalTitle').textContent = 'Add Package';
    document.getElementById('packageForm').action = '/packages';
    document.getElementById('packageForm').reset();
    // Remove the pid field for new packages to avoid validation error
    const pidField = document.getElementById('packageId');
    pidField.removeAttribute('name');
    pidField.value = '';
    const modal = new bootstrap.Modal(document.getElementById('packageModal'));
    modal.show();
}

function editPackage(button) {
    const id = button.getAttribute('data-id');
    const name = button.getAttribute('data-name');
    const desc = button.getAttribute('data-desc');
    const price = button.getAttribute('data-price');
    
    document.getElementById('packageModalTitle').textContent = 'Edit Package';
    // Re-add the name attribute for edit
    const pidField = document.getElementById('packageId');
    pidField.setAttribute('name', 'pid');
    pidField.value = id;
    document.getElementById('pname').value = name;
    document.getElementById('pdescription').value = desc;
    document.getElementById('ppriced').value = price;
    document.getElementById('packageForm').action = '/update-packages/' + id;
    
    const modal = new bootstrap.Modal(document.getElementById('packageModal'));
    modal.show();
}
