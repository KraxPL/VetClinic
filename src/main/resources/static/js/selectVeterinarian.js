const form = document.querySelector('#select-veterinarian-form');
form.addEventListener('submit', (event) => {
    event.preventDefault();

    const veterinarian = document.querySelector('#veterinarian').value;
    const name = document.querySelector('#name').value;

    axios.get('/create-chat-room', {
        params: {
            veterinarian: veterinarian,
            name: name
        }
    })
        .then(response => {
            window.location.href = response.data;
        })
        .catch(error => {
            console.error(error);
        });
});