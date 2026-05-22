const logoutBtn = document.getElementById('logoutBtn');
const logoutModal = document.getElementById('logoutModal');
const confirmLogout = document.getElementById('confirmLogout');
const cancelLogout = document.getElementById('cancelLogout');

// ログアウトボタンをクリックした時にモーダルを表示
logoutBtn.addEventListener('click', () => {
  logoutModal.classList.remove('hidden');
});

// キャンセルボタンをクリックした時にモーダルを閉じる
cancelLogout.addEventListener('click', () => {
  logoutModal.classList.add('hidden');
});

// ログアウトを実行した時の処理（例：サーバーへリクエスト）
confirmLogout.addEventListener('click', () => {
  // ここに実際のログアウト処理（Fetch APIなど）を記述します
  window.location.href = '/logout-endpoint'; // ログアウト処理先のURL
});

// モーダルの背景部分をクリックした時も閉じる場合
logoutModal.addEventListener('click', (e) => {
  if (e.target === logoutModal) {
    logoutModal.classList.add('hidden');
  }
});