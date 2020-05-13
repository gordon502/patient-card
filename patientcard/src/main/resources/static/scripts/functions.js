const patientsTableLegend = `    <tr>
                                    <th onclick="sortTable(0)">ID</th>
                                    <th onclick="sortTable(1)">Imię i nazwisko</th>
                                    <th onclick="sortTable(2)">Płeć</th>
                                    <th onclick="sortTable(3)">Data urodzenia</th>
                                    <th onclick="sortTable(4)">Adres zamieszkania</th>
                                    <th onclick="sortTable(5)">Nr telefonu</th>
                                    <th>Szczegóły</th>
                                </tr>`;

function fillTable() {
    var URL = 'http://localhost:8081/Patients'
    const param = document.getElementById('surname').value;
    if (param.length != 0) {
        URL += '?name=' + param;
    }
    fetch(URL)
        .then(response => response.json())
        .then(data => {
            var tableStructure = '';
            for (var i = 0; i < data.length; i++) {
                var p = data[i];

                tableStructure +=
                        `<tr>
                            <td id="id${i}">${p["id"]}</td>
                            <td>${p["name"]}</td>
                            <td>${p["gender"]}</td>
                            <td>${p["birthDate"]}</td>
                            <td>${p["address"]}</td>
                            <td>${p["telecom"]}</td>
                            <td><button id="btn${i}">Przejdź</button></td>  
                         </tr>`;
            }
            document.getElementById("rowToInsert").innerHTML = tableStructure;
            sortTable(1);
        });
    return true;
}

function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("patientsTable");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount ++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}