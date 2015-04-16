$ ->
  $.get "/searches", (searchTerm) ->
    $.each searchTerm,(index,searchTerm) ->
      $('#resultContainer').append $("<li>").text searchTerm.searchTerm