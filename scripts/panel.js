$('#create-news-area').summernote({
    placeholder: 'Текст новини',
    tabsize: 3,
    height: 300,
    lang: "uk-UA",
    toolbar: [
        // [groupName, [list of button]]
        ['fontstyle', ['bold', 'italic', 'underline', 'clear']],
        ['fontsize', ['fontsize']],
        ['fontfamily', ['fontname']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['insert', ['link', 'picture', 'video']],
        ['misk', ['undo', 'redo']],
        ['misk', ['fullscreen', 'help']]
      ]
  });

  $('#edit-news-area').summernote({
    placeholder: 'Текст новини',
    tabsize: 3,
    height: 300,
    lang: "uk-UA",
    toolbar: [
        // [groupName, [list of button]]
        ['fontstyle', ['bold', 'italic', 'underline', 'clear']],
        ['fontsize', ['fontsize']],
        ['fontfamily', ['fontname']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['insert', ['link', 'picture', 'video']],
        ['misk', ['undo', 'redo']],
        ['misk', ['fullscreen', 'help']]
      ]
  });

  let blocks = [...$("#blocks>div")];

  function hideBlocks() { 
    blocks.forEach((el) => {
        el.style.display = "none";
    })
   }

  $("#create_news").on('click', (e => {
    e.preventDefault();
    hideBlocks();
    $("#create-news").toggle();
  }));

  $("#news_list").on('click', (e => {
    e.preventDefault();
    hideBlocks();
    $("#edit-delete-news").toggle();
  }))

  $("#change-credentials-btn").on('click', (e => {
    e.preventDefault();
    hideBlocks();
    $("#change-credentials").toggle();
  }))



