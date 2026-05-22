function confirmLogout() {
  if (window.confirm('ログアウトしますか？')) {
    // ログアウト処理の実行
    window.location.href = '/logout'; 
  }
}
