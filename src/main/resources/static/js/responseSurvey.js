
// Roster of colors to color charts with
let COLORS = [
    'rgb(54, 162, 235)',
    'rgb(255, 99, 132)',
    'rgb(255, 159, 64)',
    'rgb(75, 192, 192)',
    'rgb(153, 102, 255)',
    'rgb(255, 205, 86)',
    'rgb(201, 203, 207)'
];

// Opacity value for coloring in charts
let ALPHA = 0.5;

// A global counter to help recycle chart colors
let colorCounter = 0;

// Return the next color to use
function getColor(){
    return COLORS[colorCounter++ % COLORS.length];
}

// Modify the provided color to have opacity = ALPHA
function transparentize(color) {
    return Color(color).alpha(ALPHA).rgbString();
}

// On Load
$(document).ready(() => {
    // Update all containers to container charts rather than tables
    $('.question-container').each((_, c) => {
        let container = $(c);
        let type = container.attr('data-question-type');

        if (type === "TEXT"){
            // Table is fine here
        } else if (type === "NUMBER"){
            parseDataNumber(container);
        } else if (type === "MC"){
            parseDataMC(container);
        } else {
            console.error(`Unknown question type: ${type}`);
        }
    });
});

// Extract the histogram data from the table
function parseDataNumber(container){
    // Get the min and max from the html
    let min = container.attr('data-question-min');
    let max = container.attr('data-question-max');

    // map range values to the number of occurrences
    let valueMap = {};
    for (let i = min; i <= max; i++){
        valueMap[i] = 0;  // need to add these in case they have no occurences
    }

    // count up the data elements
    container.find('tbody > tr').each((_, r) => {
        let row = $(r);
        if (row[0].cells.length === 2){
            valueMap[parseInt(row[0].cells[0].innerText)] = parseInt(row[0].cells[1].innerText);
        }
    });

    buildHistogram(container, valueMap);
}

// Extract the pia chart data from the table
function parseDataMC(container){

    // record the labels and the number of occurrences
    let labels = [];
    let values = [];

    // count up the data elements
    container.find('tbody > tr').each((_, r) => {
        let row = $(r);
        if (row[0].cells.length === 2){
            labels.push(row[0].cells[0].innerText);
            values.push(parseInt(row[0].cells[1].innerText));
        }
    });

    buildPieChart(container, labels, values);
}

// Draw a histogram with the provided data
function buildHistogram(container, dataMap){
    // save the question name
    let title = container.find('h2').text();

    // clear the table
    container.empty();

    // restore question name
    container.append(`<h2>${title}</h2>`);

    // add a canvas
    container.append('<canvas width="400" height="400"></canvas><br><br>');

    // grab a reference to the canvas
    let ctx = container.find('canvas');

    // Need to sort the keys to properly display the range
    let keys = Object.keys(dataMap);

    keys.sort((a, b) => {
        return a - b;
    });
    let values = [];
    for (key of keys){
        values.push(dataMap[key]);
    }

    // Build color lists for displaying the bars
    let backgroundColorList = [];
    let borderColorList = [];

    for (let i = 0; i < values.length; i++){
        let color = 'rgba(0, 0, 0, 0)';  // default -- should never show up
        if (values[i] !== 0){
            color = getColor();
        }
        borderColorList.push(color);
        backgroundColorList.push(transparentize(color));
    }

    // build the actual chart
    let chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: keys,
            datasets: [{
                data: values,
                backgroundColor: backgroundColorList,
                borderColor: borderColorList,
                borderWidth: 1
            }]
        },
        options: {
            responsive: false,
            legend: {
                display: false,
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}

// Draw a pie chart with the provided data
function buildPieChart(container, labels, values){
    // save the question
    let title = container.find('h2').text();

    // clear the table
    container.empty();

    // restore the question
    container.append(`<h2>${title}</h2>`);

    // add a canvas
    container.append('<canvas width="400" height="400"></canvas><br><br>');

    // grab a reference to the canvas
    let ctx = container.find('canvas');

    // Build color lists for displaying the slices
    let backgroundColorList = [];
    let borderColorList = [];

    for (let i = 0; i < labels.length; i++){
        let color = getColor();
        backgroundColorList.push(transparentize(color));
        borderColorList.push(color);
    }

    // build the actual chart
    let chart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: backgroundColorList,
                borderColor: borderColorList,
                borderWidth: 1
            }]
        },
        options: {
            responsive: false,
        }
    });
}
