function loadSchedule() {
    const vetId = document.getElementById("vetSelect").value;
    const scheduleTable = document.getElementById("scheduleTable");

    while (scheduleTable.firstChild) {
        scheduleTable.removeChild(scheduleTable.firstChild);
    }

    if (vetId === "") return;

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            const schedule = JSON.parse(this.responseText);

            const headerRow = document.createElement("tr");
            const daysOfWeek = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
            for (const day of daysOfWeek) {
                if (schedule.hasOwnProperty(day.toUpperCase())) {
                    const headerCell = document.createElement("th");
                    headerCell.innerHTML = day;
                    headerRow.appendChild(headerCell);
                }
            }
            scheduleTable.appendChild(headerRow);

            const hours = Object.values(schedule);
            for (let i = 0; i < hours[0].length; i++) {
                const hourRow = document.createElement("tr");

                for (const day of daysOfWeek) {
                    if (schedule.hasOwnProperty(day.toUpperCase())) {
                        const hourCell = document.createElement("td");
                        const hour = hours.find((value) => value[i]);
                        if (hour && hour[i]) {
                            const button = document.createElement("button");
                            button.innerHTML = hour[i];

                            if (!schedule[day.toUpperCase()].includes(hour[i])) {
                                button.disabled = true;
                            }

                            hourCell.appendChild(button);
                        }
                        hourRow.appendChild(hourCell);
                    }
                }

                scheduleTable.appendChild(hourRow);
            }
        }
    };
    xhttp.open("GET", "/booking?vetId=" + vetId, true);
    xhttp.send();
}