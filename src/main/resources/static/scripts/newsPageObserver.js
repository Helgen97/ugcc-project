(()=>{"use strict";let e=document.getElementById("mainNewsEnd"),t=document.getElementById("otherNewsListContainer");var n;(n=async function(){await fetch("/api/news/pages/random").then((e=>e.json())).then((({content:e})=>{let n=e.map((e=>(({namedId:e,imageUrl:t,imagesUrls:n=[],title:i,creationDate:s,description:a},c,r=!1)=>{let o=document.createElement("a");o.setAttribute("href",`/${c}/${e}`),o.setAttribute("class","section");let l=document.createElement("div");l.setAttribute("class","section__image-container");let d=document.createElement("img");d.setAttribute("src",t||n[0]),d.setAttribute("loading","lazy"),d.setAttribute("alt",i),d.setAttribute("class","section__image");let m=document.createElement("div");m.setAttribute("class","section__info");let u=document.createElement("div");u.setAttribute("class","section__info__date");let p=document.createElement("p");p.innerText=s;let _=document.createElement("div");_.setAttribute("class","section__info__title");let b=document.createElement("p");if(b.innerText=i,l.append(d),u.append(p),_.append(b),m.append(u,_),r){let e=document.createElement("div");e.setAttribute("class","section__info__description");let t=document.createElement("p");t.innerText=a,e.append(t),m.append(e)}return o.append(l,m),o})(e,"news")));t.append(...n)})).catch((e=>{console.error(e)}))},new IntersectionObserver(((e,t)=>{e.forEach((async e=>{e.isIntersecting&&(await n(),t.unobserve(e.target))}))}))).observe(e)})();