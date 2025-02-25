document.querySelector("form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = {
        name: document.querySelector("[name='name']").value,
        surname: document.querySelector("[name='surname']").value,
        email: document.querySelector("[name='email']").value,
        password: document.querySelector("[name='password']").value
    };

    try {
        let response = await fetch("/api/registration", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        });
        let result = await response.json();
        if (response.ok) {
            window.location.href = "/email-verification";
        } else {
            document.querySelector(".error-message p").innerText = result.message;
            document.querySelector(".error-message").style.display = "block";
        }
    } catch (error) {
        console.error("Registration failed", error);
    }
});
