$('select[data-value]').each(function(index, el){
    const $el = $(el);

    const defaultValue = $el.attr('data-value');
    if(defaultValue.length > 0) {
    $el.val(defaultValue);
    }
});

 //$('select[data-value]') : select 를 검색한다는것인데 데이터벨류를 검색한다는 속성을 가지고있다.
// 각각 실행하라
// 이런 특징을 가진 값을 검색하면 index : 순번  el : 엘리먼트

// 최초에 $('select[data-value]') 이런 속성을 찾아내는데, $el.attr('data-value');에서 속성을 딱 한번 받아오고
// 받아온 속성을  $el.val(defaultValue); 여기 엘리먼트에게 넣어준다.
// view-source:http://localhost:8081/usr/article/list?boardId=1&searchKeywordTypeCode=title&searchKeyword=11&page=5로 검색하고 ctrl + u해서 확인해볼 수 있다.

