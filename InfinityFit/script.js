//----------------------------------칼로리 계산기----------------------------------//
document.getElementById('calorieForm').addEventListener('submit', function (e) {
    e.preventDefault();

    // 입력값 가져오기
    const weight = parseFloat(document.getElementById('weight').value);
    const targetWeight = parseFloat(document.getElementById('targetWeight').value);
    const age = parseInt(document.getElementById('age').value);
    const goalPeriod = parseInt(document.getElementById('goalPeriod').value);
    const gender = document.querySelector('input[name="gender"]:checked').value;

    if (isNaN(weight) || isNaN(targetWeight) || isNaN(age) || isNaN(goalPeriod) || !gender) {
        document.getElementById('calorieResult').innerText = '모든 값을 올바르게 입력하세요.';
        return;
    }

    // 기초 대사량(BMR) 계산
    let bmr;
    if (gender === 'male') {
        bmr = 10 * weight + 6.25 * 170 - 5 * age + 5; // 남성 기준 키 170cm
    } else {
        bmr = 10 * weight + 6.25 * 160 - 5 * age - 161; // 여성 기준 키 160cm
    }

    // 활동계수 설정
    const activityMultiplier = 1.55;
    const maintenanceCalories = bmr * activityMultiplier; // 현재 체중 유지 칼로리

    // 총 변화량과 필요 칼로리 계산
    const totalChange = weight - targetWeight; // 감량 시 양수
    const totalCaloriesNeeded = Math.abs(totalChange) * 7700; // 총 변화에 필요한 칼로리
    const dailyCalorieAdjustment = totalCaloriesNeeded / goalPeriod; // 하루 칼로리 조정량

    // 섭취와 운동 비율 설정 (8:2)
    const calorieReductionFromDiet = dailyCalorieAdjustment * 0.8; // 섭취 제한
    const calorieReductionFromExercise = dailyCalorieAdjustment * 0.2; // 운동 소모

    // 하루 권장 섭취량 계산 (운동 칼로리를 더 먹을 수 있음)
    const dailyCalories = maintenanceCalories - calorieReductionFromDiet + calorieReductionFromExercise;

    // 하루 추가 운동량 계산
    const dailyExerciseCalories = calorieReductionFromExercise;

    // 경고 메시지 및 결과 처리
    if (dailyCalories < 1200) {
        document.getElementById('calorieResult').innerHTML = `
            <p style="color: red;">⚠️ 하루 권장 섭취량이 1200kcal 이하입니다. 건강을 해칠 수 있으니 전문가와 상의하세요!</p>
        `;
    } else {
        document.getElementById('calorieResult').innerHTML = `
            <p>현재 기초 대사량(BMR): ${Math.round(bmr)} kcal</p>
            <p>현재 체중 유지에 필요한 권장 섭취량: ${Math.round(maintenanceCalories)} kcal</p>
            <p>하루 권장 섭취량: ${Math.round(dailyCalories)} kcal</p>
            <p>하루 추가 운동량: ${Math.round(dailyExerciseCalories)} kcal</p>
        `;
    }
});





//----------------------------------음식 칼로리 사전----------------------------------//
// 음식 칼로리 사전 데이터베이스
const foodDatabase = {
   "바나나": 89,
    "사과": 52,
    "닭가슴살": 165,
    "밥": 200,
    "피자": 285,
    "감자": 77,
    "고구마": 86,
    "당근": 41,
    "오이": 15,
    "브로콜리": 34,
    "아보카도": 160,
    "토마토": 18,
    "양파": 40,
    "마늘": 149,
    "파프리카": 20,
    "양배추": 25,
    "상추": 14,
    "시금치": 23,
    "호박": 26,
    "버섯": 22,
    "김치": 33,
    "두부": 76,
    "계란": 68,
    "우유": 42,
    "요거트": 59,
    "치즈": 402,
    "치킨": 239,
    "돼지고기": 242,
    "소고기": 250,
    "연어": 208,
    "참치": 184,
    "고등어": 205,
    "명태": 72,
    "멸치": 109,
    "오징어": 92,
    "새우": 99,
    "굴": 68,
    "쌀국수": 130,
    "라면": 500,
    "파스타": 131,
    "스파게티": 220,
    "햄버거": 295,
    "핫도그": 260,
    "감자칩": 536,
    "팝콘": 375,
    "초콜릿": 546,
    "케이크": 257,
    "쿠키": 502,
    "아이스크림": 207,
    "빵": 265,
    "토스트": 250,
    "크로와상": 406,
    "베이글": 289,
    "피넛버터": 588,
    "잼": 260,
    "꿀": 304,
    "소금": 0,
    "설탕": 387,
    "식용유": 884,
    "올리브오일": 884,
    "버터": 717,
    "마가린": 717,
    "마요네즈": 680,
    "케첩": 112,
    "머스터드": 66,
    "간장": 53,
    "된장": 198,
    "고추장": 186,
    "김": 25,
    "떡볶이": 215,
    "순대": 242,
    "불고기": 185,
    "갈비탕": 116,
    "설렁탕": 110,
    "비빔밥": 500,
    "삼겹살": 518,
    "불닭볶음면": 530,
    "잡채": 110,
    "갈비찜": 215,
    "족발": 234,
    "보쌈": 240,
    "칼국수": 186,
    "김밥": 150,
    "라볶이": 290,
    "우동": 170,
    "된장찌개": 80,
    "김치찌개": 120,
    "순두부찌개": 92,
    "떡국": 330,
    "갈비구이": 330,
    "조기구이": 120,
    "갈치구이": 130,
    "장어구이": 250,
    "고등어구이": 170,
    "생선회": 100,
    "초밥": 300,
    "돈까스": 400,
    "닭강정": 320,
    "탕수육": 360,
    "짜장면": 500,
    "짬뽕": 550,
    "마라탕": 350,
    "꿔바로우": 370,
    "깐풍기": 330,
    "깐쇼새우": 310,
    "양꼬치": 200,
    "샤브샤브": 150,
    "훠궈": 180,
    "스테이크": 250,
    "폭립": 290,
    "훈제오리": 250,
    "연어스테이크": 280,
    "달걀찜": 60,
    "계란말이": 80,
    "어묵": 85,
    "튀김": 400,
    "감자튀김": 310,
    "새우튀김": 200,
    "오징어튀김": 190,
    "치킨너겟": 300,
    "비엔나소시지": 220,
    "핫바": 250,
    "스팸": 430,
    "순대국": 150,
    "육개장": 170,
    "추어탕": 110,
    "곰탕": 120,
    "콩나물국": 60,
    "미역국": 60,
    "소고기무국": 100,
    "동태찌개": 120,
    "닭죽": 150,
    "전복죽": 150,
    "팥죽": 180,
    "호박죽": 110,
    "죽순밥": 120,
    "낙지볶음": 180,
    "곱창볶음": 290,
    "삼계탕": 210,
    "치즈볼": 250,
    "우엉차": 2,
    "녹차": 1,
    "아메리카노": 5,
    "라떼": 120,
    "카푸치노": 110,
    "핫초코": 200,
    "밀크티": 150,
    "레모네이드": 120,
    "콜라": 140,
    "사이다": 130,
    "맥주": 150,
    "와인": 125,
    "위스키": 250,
    "소주": 150
};

// 음식 칼로리 검색 버튼 클릭 이벤트
document.getElementById('searchFood').addEventListener('click', function () {
    const foodName = document.getElementById('foodSearch').value.trim().toLowerCase();

    if (foodName === '') {
        alert('음식 이름을 입력하세요!');
        return;
    }

    const normalizedFoodDatabase = Object.fromEntries(
        Object.entries(foodDatabase).map(([key, value]) => [key.toLowerCase(), value])
    );

    const result = normalizedFoodDatabase[foodName];
    const resultDiv = document.getElementById('foodResult');

    if (result) {
        resultDiv.innerText = `${foodName}의 칼로리: ${result} kcal`;
    } else {
        resultDiv.innerText = '데이터베이스에 없는 음식입니다.';
    }
});

//----------------------------------다이어트 캘린더----------------------------------//
// DOM 요소 가져오기
const calendarBody = document.getElementById("calendarBody");
const monthYear = document.getElementById("monthYear");
const selectedDateElement = document.getElementById("selectedDate");
const mealInput = document.getElementById("mealCalories");
const exerciseInput = document.getElementById("exerciseCalories");
const weightInput = document.getElementById("weightInput");
const saveButton = document.getElementById("saveData");

let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();
let selectedDate = null;
const healthData = JSON.parse(localStorage.getItem("healthData")) || {};

// 달력 렌더링 함수
function renderCalendar(year, month) {
    calendarBody.innerHTML = ""; // 기존 달력 초기화
    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();

    monthYear.textContent = `${year}년 ${month + 1}월`;

    let row = document.createElement("tr");
    for (let i = 0; i < firstDay; i++) {
        row.appendChild(document.createElement("td")); // 공백 셀 추가
    }

    for (let date = 1; date <= lastDate; date++) {
        const cell = document.createElement("td");
        const dateString = `${year}-${String(month + 1).padStart(2, "0")}-${String(date).padStart(2, "0")}`;
        cell.textContent = date;

        // healthData에서 해당 날짜의 데이터를 가져옴
        if (healthData[dateString]) {
            const { meal, exercise, weight } = healthData[dateString];
            cell.innerHTML += `
                ${meal ? `<div>🍴 ${meal} kcal</div>` : ""}
                ${exercise ? `<div>💪 ${exercise} kcal</div>` : ""}
                ${weight ? `<div>⚖️ ${weight} kg</div>` : ""}
            `;
        }

        // 날짜 클릭 이벤트 추가
        cell.addEventListener("click", () => {
            document.querySelectorAll("td").forEach((td) => td.classList.remove("selected"));
            cell.classList.add("selected");
            selectedDate = dateString;
            selectedDateElement.textContent = `선택된 날짜: ${dateString}`;

            // 선택된 날짜의 데이터 불러오기
            const data = healthData[dateString] || {};
            mealInput.value = data.meal || "";
            exerciseInput.value = data.exercise || "";
            weightInput.value = data.weight || "";
        });

        row.appendChild(cell);

        if ((date + firstDay) % 7 === 0) {
            calendarBody.appendChild(row);
            row = document.createElement("tr");
        }
    }

    if (row.childNodes.length > 0) {
        while (row.childNodes.length < 7) {
            row.appendChild(document.createElement("td"));
        }
        calendarBody.appendChild(row);
    }
}

// 데이터 저장
saveButton.addEventListener("click", () => {
    if (!selectedDate) {
        alert("날짜를 선택하세요.");
        return;
    }

    const meal = parseInt(mealInput.value) || null; // 입력하지 않으면 null
    const exercise = parseInt(exerciseInput.value) || null; // 입력하지 않으면 null
    const weight = parseFloat(weightInput.value) || null; // 입력하지 않으면 null

    healthData[selectedDate] = { meal, exercise, weight };
    localStorage.setItem("healthData", JSON.stringify(healthData));

    alert("데이터가 저장되었습니다.");
    renderCalendar(currentYear, currentMonth);

    // 그래프 갱신 (몸무게 그래프 관련 함수 호출)
    updateWeightGraph();
});

// 이전/다음 달 버튼
document.getElementById('prevMonth').addEventListener("click", () => {
    currentMonth--;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    renderCalendar(currentYear, currentMonth);
});

document.getElementById('nextMonth').addEventListener("click", () => {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    renderCalendar(currentYear, currentMonth);
});

// 초기 렌더링
renderCalendar(currentYear, currentMonth);

//----------------------------------몸무게 변화 그래프----------------------------------//

let weightChart;

// 몸무게 그래프 갱신 함수
function updateWeightGraph() {
    const labels = []; // 날짜 리스트
    const weights = []; // 몸무게 리스트

    // healthData에서 날짜와 몸무게 데이터를 추출
    for (const date in healthData) {
        const weight = healthData[date]?.weight;
        if (weight) {
            labels.push(date); // x축: 날짜
            weights.push(weight); // y축: 몸무게
        }
    }

    const ctx = document.getElementById("weightChart").getContext("2d");

    if (!weightChart) {
        // 처음에만 Chart.js 객체 생성
        weightChart = new Chart(ctx, {
            type: "line",
            data: {
                labels: labels, // 날짜 데이터
                datasets: [
                    {
                        label: "몸무게 (kg)",
                        data: weights, // 몸무게 데이터
                        borderColor: "rgba(75, 192, 192, 1)",
                        backgroundColor: "rgba(75, 192, 192, 0.2)",
                        tension: 0.1, // 곡선의 부드러움
                    },
                ],
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: "날짜",
                        },
                    },
                    y: {
                        title: {
                            display: true,
                            text: "몸무게 (kg)",
                        },
                    },
                },
            },
        });
    } else {
        // 기존 차트를 업데이트
        weightChart.data.labels = labels;
        weightChart.data.datasets[0].data = weights;
        weightChart.update();
    }
}

// 페이지 로드 시 그래프 초기화
document.addEventListener("DOMContentLoaded", () => {
    updateWeightGraph();
});
