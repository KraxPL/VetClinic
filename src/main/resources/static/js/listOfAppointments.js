const currentDate = new URLSearchParams(window.location.search).get('date') || new Date().toISOString().slice(0, 10);
document.getElementById("datepicker").value = currentDate;
function nextDay() {
    const datepicker = document.getElementById("datepicker");
    const date = new Date(datepicker.value);
    date.setDate(date.getDate() + 1);
    const dateString = date.toISOString().slice(0, 10);
    datepicker.value = dateString;
    window.location.href = "/booking/appointments?date=" + dateString;
}
function prevDay() {
    const datepicker = document.getElementById("datepicker");
    const date = new Date(datepicker.value);
    date.setDate(date.getDate() - 1);
    const dateString = date.toISOString().slice(0, 10);
    datepicker.value = dateString;
    window.location.href = "/booking/appointments?date=" + dateString;
}
function changeDate() {
    const datepicker = document.getElementById('datepicker');
    const date = new Date(datepicker.valueAsNumber);
    const formattedDate = date.toISOString().slice(0, 10);

    const url = new URL(window.location.href);
    url.searchParams.set('date', formattedDate);
    window.history.pushState({}, '', url);
}