window.addEventListener('DOMContentLoaded', () => {
    const table = document.querySelector('.table');
    const rows = Array.from(table.querySelectorAll('tr')).slice(1);

    const sortTable = (sortBy) => {
        rows.sort((a, b) => {
            const aValue = a.querySelector(`[data-sort="${sortBy}"]`).textContent.trim().toUpperCase();
            const bValue = b.querySelector(`[data-sort="${sortBy}"]`).textContent.trim().toUpperCase();
            if (aValue < bValue) return -1;
            if (aValue > bValue) return 1;
            return 0;
        });
        const sortedRows = [table.querySelector('tr:first-child'), ...rows];
        sortedRows.forEach(row => {
            if (row !== table.querySelector('tr:first-child')) {
                table.appendChild(row);
            }
        });
    };

    const sortHeaders = Array.from(document.querySelectorAll('.table th[data-sort]'));
    sortHeaders.forEach(header => {
        header.addEventListener('click', () => {
            const sortBy = header.getAttribute('data-sort');
            sortTable(sortBy);
        });
    });
    sortTable('name');
});
