<template>
  <footer>
    <div class="footer-content">
      <div class="footer-logo">
        <h2>tofit</h2>
        <p>운동 초보자를 위한 전문가 영상 및 커뮤니티</p>
      </div>
      <div class="footer-links">
        <ul>
          <li><a href="/about">About Us</a></li>
          <li><a href="/contact">Contact</a></li>
          <li><a href="/privacy-policy">Privacy Policy</a></li>
        </ul>
      </div>
      <div class="footer-social">
        <ul>
          <li>
            <a href="https://facebook.com/tofit" target="_blank">Facebook</a>
          </li>
          <li>
            <a href="https://twitter.com/tofit" target="_blank">Twitter</a>
          </li>
          <li>
            <a href="https://instagram.com/tofit" target="_blank">Instagram</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2024 tofit.com. All rights reserved.</p>
    </div>
  </footer>
</template>

<script setup></script>

<style scoped>
footer {
  background-color: #f8f8f8;
  padding: 30px 0;
  color: #333;
  font-family: Arial, sans-serif;
  border-top: 1px solid #e0e0e0; /* 연한 회색 테두리 추가 */
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.footer-logo h2 {
  font-size: 36px;
  color: #3a3a3a;
  margin: 0;
}

.footer-logo p {
  font-size: 14px;
  color: #888;
}

.footer-links ul,
.footer-social ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.footer-links li,
.footer-social li {
  margin-bottom: 10px;
}

.footer-links a,
.footer-social a {
  text-decoration: none;
  color: #333;
  font-size: 16px;
}

.footer-links a:hover,
.footer-social a:hover {
  color: #0066cc;
}

.footer-social ul {
  display: flex;
  gap: 15px;
}

.footer-bottom {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #888;
}

.footer-bottom p {
  margin: 0;
}
</style>
