var Fulldata = {};

$(document).ready(function () {
    getAndInsertPatientData();
});


function getAndInsertPatientData() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get("id");

    const URL = 'http://localhost:8081/fulldata/' + id;

    fetch(URL)
        .then(response => response.json())
        .then(data => {
            Fulldata.patient = data["patient"];
            Fulldata.observations = data["observations"];
            Fulldata.medicationRequests = data["medicationRequests"];

            insertPatientInfo();
            mergeObservationAndRequest();
            fillTimeLine(Fulldata.sorted);
            fillChart(Fulldata.sorted, "weight");
        });


}

function insertPatientInfo(patient) {
    document.getElementById("rowToInsert").innerHTML = `
        <tr>
            <td>${Fulldata.patient["id"]}</td>
            <td>${Fulldata.patient["name"]}</td>
            <td>${Fulldata.patient["gender"]}</td>
            <td>${Fulldata.patient["birthDate"]}</td>
            <td>${Fulldata.patient["address"]}</td>
            <td>${Fulldata.patient["telecom"]}</td>
        </tr>
    `;
}

function sss() {
    document.getElementById("eldo").innerText = Fulldata.observations[0]["category"];
}

function mergeObservationAndRequest() {
    var sum = [];
    Fulldata.observations.forEach(obs => {
        sum.push(obs);
    });
    sum.sort(GetSortOrder("name"));
    sum.reverse();

    //cant sort different jsons, so tricky way to do it
    Fulldata.medicationRequests.forEach(med => {
        for (var i = 0; i < sum.length; i++) {
            if (sum[i]["date"] <  med["date"]) {
                sum.splice(i, 0, med);
                break;
            }

        }
    });

    Fulldata.sorted = sum;
}

function fillTimeLine(sum) {
    const itemss = [];


    sum.forEach(elem => {

        // OBSERVATION
        if (elem["category"] != null) {

            //NO DETAILS
            if (elem["value"] != null) {
                itemss.push({
                    type: 'smallItem',
                    label: `<b>${elem["date"].slice(0, 10)}</b><br>${elem["date"].slice(11, 19)}`,
                    shortContent: `<b>OBSERVATION: ${elem["category"]}</b><br>
                                           ${elem["code"]}: ${elem["value"]}`
                })
            }

            //DETAILS
            else if (elem["components"] != null) {
                var comps = "";
                elem["components"].forEach(comp => {
                    comps += comp["text"] + ": " + comp["value"] + "<br>";
                })
                itemss.push({
                    type: 'smallItem',
                    label: `<b>${elem["date"].slice(0, 10)}</b><br>${elem["date"].slice(11, 19)}`,
                    shortContent: `<b>OBSERVATION: ${elem["category"]}</b><br>
                                           ${elem["code"]}`,
                    fullContent: `<b>OBSERVATION: ${elem["category"]}</b><br>
                                          ${comps}`,
                    showMore: `<div style="color: blue;">show more</div>`,
                    showLess: `<div style="color: blue;">show less</div>`
                });
            }

            //REST
            else {
                itemss.push({
                    type: 'smallItem',
                    label: `<b>${elem["date"].slice(0, 10)}</b><br>${elem["date"].slice(11, 19)}`,
                    shortContent: `<b>OBSERVATION: ${elem["category"]}</b><br>
                                           ${elem["code"]}`
                });
            }
        }

        //MEDICATIONREQUEST
        else {
            var dosage = "";
            if (elem["dosage"] != null) {
                dosage = "<br>Dosage instruction: " + elem["dosage"];
            }

            itemss.push({
                type: 'smallItem',
                label: `<b>${elem["date"].slice(0, 10)}</b><br>${elem["date"].slice(11, 19)}`,
                shortContent: `<b>MEDICATION REQUEST</b><br>
                               ${elem["text"]}`,
                fullContent: `<b>MEDICATION REQUEST</b><br>
                               ${elem["text"]}<br>
                               status: ${elem["status"]}<br>
                               intent: ${elem["intent"]}` + dosage,
                showMore: `<div style="color: blue;">show more</div>`,
                showLess: `<div style="color: blue;">show less</div>`
            });
        }
    });
    $('#timeline-container').timelineMe({
        items: itemss
    });
}

function GetSortOrder(prop) {
    return function(a, b) {
        if (a[prop] > b[prop]) {
            return 1;
        } else if (a[prop] < b[prop]) {
            return -1;
        }
        return 0;
    }
}

function filterTimeLine() {
    const dateFrom = document.getElementById("dateFrom").value;
    const dateTo = document.getElementById("dateTo").value;
    var dateFromConv = dateFrom.slice(6) + "-" + dateFrom.slice(3,5) + "-" + dateFrom.slice(0,2);
    var dateToConv = dateTo.slice(6) + "-" + dateTo.slice(3,5) + "-" + dateTo.slice(0,2);

    if (dateFrom.length == 0) {
        dateFromConv = "1800-01-01";
    }
    if (dateTo.length == 0) {
        dateToConv = "2400-01-01";
    }

    const filteredJsons = [];

    Fulldata.sorted.forEach(elem => {
        if (dateToConv >= elem["date"].slice(0, 10) && elem["date"].slice(0,10) >= dateFromConv ) {
            filteredJsons.push(elem);
        }
    });

    $("#timeline-container").timelineMe("destroy");
    fillTimeLine(filteredJsons);

}

function fillChart(sorted, option) {
    const dateFrom = document.getElementById("dateFrom").value;
    const dateTo = document.getElementById("dateTo").value;
    var dateFromConv = dateFrom.slice(6) + "-" + dateFrom.slice(3,5) + dateFrom.slice(0,2);
    var dateToConv = dateTo.slice(6) + "-" + dateTo.slice(3,5) + dateTo.slice(0,2);
    var dataPoints = [];

    if (dateFrom.length == 0) {
        dateFromConv = "1800-01-01";
    }
    if (dateTo.length == 0) {
        dateToConv = "2400-01-01";
    }


    var title = option;
    var yTitle = "";
    var code = "";

    if (option == "body mass index") {
        yTitle = "[kg / m2]";
        code = "Body Mass Index";
    }
    else if (option == "weight") {
        yTitle = "[kg]";
        code = "Body Weight";
    }
    else if (option == "height") {
        yTitle = "[cm]";
        code = "Body Height";
    }
    else if (option == "temperature") {
        yTitle = "[Cel]";
        code = "Oral temperature";
    }

    for (var i = sorted.length - 1; i >= 0; i--) {
        if (sorted[i]["code"] == code) {
            var date = sorted[i]["date"];
            if (date >= dateFromConv && date <= dateToConv) {
                dataPoints.push({
                    x: new Date(date.slice(0, 10)),
                    y: parseFloat(sorted[i]["value"].slice(0, 8))
                });
            }
        }
    }

    var options =  {
        animationEnabled: true,
        theme: "light2",
        title: {
            text: title
        },
        axisX: {
            valueFormatString: "YYYY-MM-DD",
        },
        axisY: {
            title: yTitle,
            titleFontSize: 24,
            includeZero: false
        },
        data: [{
            type: "spline",
            yValueFormatString: "###.###",
            dataPoints: dataPoints
        }]
    };

    $("#chartContainer").CanvasJSChart(options);
}