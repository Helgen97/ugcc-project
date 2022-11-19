const observer = (callback) => {
    return new IntersectionObserver(((entries, observer) => {
        entries.forEach(async entry => {
            if (entry.isIntersecting) {
                await callback();
                observer.unobserve(entry.target)
            }
        })
    }));
}
export default observer;