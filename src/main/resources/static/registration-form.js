document.querySelector("form").addEventListener("submit", function(event) {
    event.preventDefault();

    const formData = {
        name: document.querySelector("input[name='name']").value,
        surname: document.querySelector("input[name='surname']").value,
        email: document.querySelector("input[name='email']").value,
        password: document.querySelector("input[name='password']").value
    };

    fetch("/api/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    }).then(r => console.log(r.data));
});
