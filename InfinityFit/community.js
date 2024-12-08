// 커뮤니티 게시글 저장소
let posts = [];

// 게시글 렌더링 함수
function renderPosts() {
    const postContainer = document.getElementById("posts");
    postContainer.innerHTML = ""; // 기존 게시글 초기화

    posts.forEach((post, index) => {
        const postDiv = document.createElement("div");
        postDiv.classList.add("post");

        // 게시글 HTML 구조
        postDiv.innerHTML = `
            <h3>${post.title}</h3>
            <p>${post.content}</p>
            <button class="deletePost" data-index="${index}">삭제</button>
        `;

        postContainer.appendChild(postDiv);
    });

    // 삭제 버튼 이벤트 연결
    document.querySelectorAll(".deletePost").forEach((button) => {
        button.addEventListener("click", function () {
            const index = this.getAttribute("data-index");
            deletePost(index);
        });
    });
}

// 게시글 작성 이벤트
document.getElementById("postForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const title = document.getElementById("postTitle").value.trim();
    const content = document.getElementById("postContent").value.trim();

    if (title === "" || content === "") {
        alert("제목과 내용을 입력하세요.");
        return;
    }

    // 게시글 추가
    posts.push({ title, content });
    renderPosts();

    // 입력 필드 초기화
    document.getElementById("postTitle").value = "";
    document.getElementById("postContent").value = "";
});

// 게시글 삭제 함수
function deletePost(index) {
    posts.splice(index, 1); // 해당 인덱스 게시글 삭제
    renderPosts(); // 다시 렌더링
}
