$(document).ready(function() {
   $("#username").blur(function() {
       $.get("register?action=checkUsername&username=" + $("#username").val(), function(response) {
           document.getElementById("username_message").innerHTML = response;
       });
   });
   $("#categoryName").keyup(function() {
       $.get("admin?action=NewCate&categoryName=" + $("#categoryName").val(), function(response) {
           document.getElementById("error_message").innerHTML = response;
       });
   });
   
   $("#itemSearch").keyup(function() {
       $.get("admin?action=checkItem&itemName=" + $("#itemSearch").val(), function(response) {
           document.getElementById("searchResult").innerHTML = response;
       });
   });
   
   $("#chosenCate").change(function() {
       $.get("admin?action=EditCategory", function(){
           var chosenCate = document.getElementById("chosenCate");
           var selectedCate = chosenCate.options[chosenCate.selectedIndex].text;
           document.getElementById("modifyCateName").value=selectedCate;
           document.getElementById("modify").value=selectedCate;
   });
});
$('[lang="es"]').hide();

$('#switch-lang').click(function() {
  $('[lang="es"]').toggle();
  $('[lang="en"]').toggle();
});
});
