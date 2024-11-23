<template>
  <div class="login-container">
    <div class="form-wrapper">
      <h2 class="form-title">로그인</h2>

      <!-- 아이디 -->
      <div class="form-group">
        <label for="userId">아이디</label>
        <input
          type="text"
          id="userId"
          v-model="id"
          class="form-control"
          placeholder="아이디를 입력하세요"
          @keyup.enter="login"
          required
        />
      </div>

      <!-- 비밀번호 -->
      <div class="form-group">
        <label for="password">비밀번호</label>
        <input
          type="password"
          id="password"
          v-model="password"
          class="form-control"
          placeholder="비밀번호를 입력하세요"
          @keyup.enter="login"
          required
        />
      </div>

      <!-- 로그인 버튼 -->
      <button @click="login" class="btn login-btn">로그인</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/user";

const store = useUserStore();
const id = ref("");
const password = ref("");

const login = function () {
  if (id.value && password.value) {
    store.userLogin(id.value, password.value);
  } else {
    alert("아이디와 비밀번호를 모두 입력해주세요.");
  }
};
</script>

<style scoped>
/* 전체 레이아웃 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}

.form-wrapper {
  width: 400px;
  text-align: left;
}

.form-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 18px;
  font-weight: bold;
}

/* form-group */
.form-group {
  margin-bottom: 20px;
}

/* label 스타일 */
label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

/* input 크기 통일 */
.form-control {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
}

/* 로그인 버튼 */
.login-btn {
  width: 100%;
  padding: 10px 0;
  background-color: #ff848f;
  color: white;
  border: none;
  border-radius: 5px;
  margin-top: 20px;
  font-size: 14px;
  font-weight: bold;
}

.login-btn:hover {
  background-color: #e86b7a;
}
</style>
