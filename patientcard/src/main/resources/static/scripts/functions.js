const patientsTableLegend = '<tr>' +
    '                    <th>ID</th>' +
    '                    <th>Imię i nazwisko</th>' +
    '                    <th>Płeć</th>' +
    '                    <th>Data urodzenia</th>' +
    '                    <th>Adres zamieszkania</th>' +
    '                    <th>Nr telefonu</th>' +
    '                </tr>';

function fillTable() {
    fetch('http://localhost:8081/Patients')
        .then(response => response.json())
        .then(data => {
            document.getElementById("patientsTable").innerHTML = patientsTableLegend;
            for (var i = 0; i < data.length; i++) {
                var p = data[i];

                document.getElementById("patientsTable")
                    .innerHTML += '<tr><td>' + p["id"] +
                    '</td><td>' + p["name"] +
                    '</td><td>' + p["gender"] +
                    '</td><td>' + p["birthDate"] +
                    '</td><td>' + p["address"] +
                    '</td><td>' + p["telecom"] + '</td></tr>';
            }
        })
}