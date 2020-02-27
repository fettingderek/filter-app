function updateTable(playerJson) {
  var playerList = JSON.parse(playerJson);
  var tableHtml = "<table id='player_table'>";
  tableHtml += "<tr>";
  tableHtml += "<th>Last</th>";
  tableHtml += "<th>First</thz>";
  tableHtml += "<th>Position</th>";
  tableHtml += "<th>Height</th>";
  tableHtml += "<th>Weight</th>";
  tableHtml += "<th>Team</th>";
  tableHtml += "</tr>";

  for (var player of playerList) {
    tableHtml += "<tr>";
    tableHtml += "<td>" + player.lastName + "</td>";
    tableHtml += "<td>" + player.firstName + "</td>";
    tableHtml += "<td>" + (player.position === null ? "" : player.position) + "</td>";
    tableHtml += "<td>" + (player.heightInInches === null
        ? ""
        : Math.floor(player.heightInInches / 12) + "'" + (player.heightInInches % 12) + '"')
        + "</td>";
    tableHtml += "<td>" + (player.weightInLbs === null ? "" : player.weightInLbs) + "</td>";
    tableHtml += "<td>" + (player.teamAbbreviation === null ? "" : player.teamAbbreviation) + "</td>";
    tableHtml += "</tr>";
  }
  tableHtml += "</table>";
  document.getElementById("table_div").innerHTML = tableHtml;
}

function getTableData() {
  const xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange=function() {
    if (this.readyState === 4 && this.status === 200) {
      updateTable(this.responseText);
    }
  };
  const searchTerm = document.getElementById('search_query_input').value;
  xhttp.open("GET", "search?name=" + searchTerm.trim(), true);
  xhttp.send();
}

window.onload = function() {
  getTableData();
};