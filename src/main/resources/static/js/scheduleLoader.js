function loadSchedule() {
    const vetId = document.getElementById("vetSelect").value;
    const mondayInput = document.getElementById("monday");
    const scheduleTable = document.getElementById("scheduleTable");

    while (scheduleTable.firstChild) {
        scheduleTable.removeChild(scheduleTable.firstChild);
    }
    if (vetId === "") return;

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const scheduleData = JSON.parse(this.responseText);
            const scheduleMap = new Map(Object.entries(scheduleData));

            const dates = Array.from(scheduleMap.keys()).sort();
            const headerRow = document.createElement("tr");

            for (const dateStr of dates) {
                const date = new Date(dateStr);
                const dayOfWeek = date.toLocaleString("en-US", { weekday: "long" });
                const dateCell = document.createElement("th");
                dateCell.innerHTML = `${dayOfWeek} (${date.toLocaleDateString()})`;
                headerRow.appendChild(dateCell);
            }

            scheduleTable.appendChild(headerRow);

            const hours = new Set();
            for (const [dateStr, hoursArray] of scheduleMap) {
                for (const hour of hoursArray) {
                    hours.add(hour);
                }
            }
            const sortedHours = Array.from(hours).sort();
            for (const hour of sortedHours) {
                const hourRow = document.createElement("tr");
                for (const dateStr of dates) {
                    const hoursArray = scheduleMap.get(dateStr);
                    const hourCell = document.createElement("td");
                    const button = document.createElement("button");
                    button.innerHTML = hour;
                    if (!hoursArray || !hoursArray.includes(hour)) {
                        button.disabled = true;
                    }
                    hourCell.appendChild(button);
                    hourRow.appendChild(hourCell);
                }
                scheduleTable.appendChild(hourRow);
            }

            const firstDate = new Date(dates[1]);
            const mondayDate = new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate());
            mondayInput.valueAsDate = mondayDate;
        }
    };
    xhttp.open("GET", "/booking?vetId=" + vetId, true);
    xhttp.send();
}

function prevWeek() {
    const monday = new Date(document.getElementById("monday").value);
    monday.setDate(monday.getDate() - 7);
    document.getElementById("monday").value = formatDate(monday);
    loadSchedule();
}

function nextWeek() {
    const monday = new Date(document.getElementById("monday").value);
    monday.setDate(monday.getDate() + 7);
    document.getElementById("monday").value = formatDate(monday);
    loadSchedule();
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    return `${year}-${month}-${day}`;
}
