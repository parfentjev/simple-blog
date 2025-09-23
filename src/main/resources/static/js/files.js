const confirmAction = () => {
  if (confirm("Permanently delete this file?")) {
    return true;
  }

  return false;
}