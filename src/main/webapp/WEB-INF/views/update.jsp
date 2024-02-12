<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>update</title>
</head>
<body>
    <form action="/board/update" method="post" name="updateForm">
        <input type="hidden" name="id" value="${board.id}" readonly>  <!-- readonly는 수정 못하고 오직 읽기만 하도록 보여짐 -->
        <input type="text" name="boardWriter" value="${board.boardWriter}" readonly>
        <input type="text" name="boardPass" id="boardPass" placeholder="비밀번호">  <!-- 자바스크립트에서 사용하려고 id="boardPass" 속성 추가 -->
        <input type="text" name="boardTitle" value="${board.boardTitle}">
        <textarea name="boardContents" cols="30" rows="10">${board.boardContents}</textarea>
        <input type="button" value="수정" onclick="updateReqFn()">  <!-- input type을 submit으로 주면 그 위에있는 값들이 그냥 제출이 되어버림. 그래서 button으로 줘서 수정 버튼 클릭 시 updateReqFn()함수를 호출하도록 함 -->
    </form>
</body>
<script>
    const updateReqFn = () => {
        const passInput = document.getElementById("boardPass").value;  <%-- 비밀번호 창에서 사용자가 입력한 값 --%>
        const passDB = '${board.boardPass}';  <%-- DB에 저장되어있는 비밀번호 값 --%>

        if (passInput == passDB) {
            document.updateForm.submit();
        } else {
            alert("비밀번호가 일치하지 않습니다!!");
        }
    }
</script>
</html>