let formModified = false;

['input', 'textarea'].flatMap((tag) => Array.from(document.getElementsByTagName(tag)))
  .forEach((element) => element.addEventListener('input', (e) => { formModified = true; }));

window.addEventListener('beforeunload', (e) => {
  if (!formModified || e.explicitOriginalTarget.type === 'submit') return;

  e.preventDefault();
})

const controlElementRows = (name, sizes) => {
  const element = document.getElementsByName(name)[0];
  if (!element) return

  let cursor = 0;
  element.addEventListener('dblclick', () => {
    element.rows = sizes[++cursor % sizes.length]
  })
}

controlElementRows('summary', [5, 25]);
controlElementRows('text', [25, 5]);
