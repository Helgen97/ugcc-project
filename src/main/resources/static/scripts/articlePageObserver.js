(()=>{"use strict";let e=document.getElementById("mainArticleEnd"),t=document.getElementById("otherArticleContainer");var n;(n=async function(){await fetch("/api/articles/pages/random").then((e=>e.json())).then((({content:e})=>{let n=e.map((e=>(({namedId:e,title:t})=>{let n=document.createElement("a");n.setAttribute("href",`/article/${e}`),n.setAttribute("class","other-article");let r=document.createElement("div");r.setAttribute("class","other-article__icon");let a=document.createElement("div");return a.setAttribute("class","other-article__title"),a.innerText=t,n.append(r,a),n})(e)));t.append(...n)})).catch((e=>{console.error(e)}))},new IntersectionObserver(((e,t)=>{e.forEach((async e=>{e.isIntersecting&&(await n(),t.unobserve(e.target))}))}))).observe(e)})();