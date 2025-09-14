let formModified = false;

['input', 'textarea'].flatMap((tag) => Array.from(document.getElementsByTagName(tag)))
  .forEach((element) => element.addEventListener('input', (e) => { formModified = true; }));

window.addEventListener('beforeunload', (e) => {
  if (!formModified || e.explicitOriginalTarget.type === 'submit') return;

  e.preventDefault();
})
