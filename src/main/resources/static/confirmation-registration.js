document.querySelector("form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let code = document.querySelector("[name='code']").value;
    const formData = new URLSearchParams();
    formData.append("code", code);

    try {
        let response = await fetch("/api/registration/code", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        });
        let result = await response.json();
        if (response.ok) {
            window.location.href = "/login";
        }
        else {
            document.querySelector(".error-message p").innerText = result.message;
            document.querySelector(".error-message").style.display = "block";
        }
    } catch (error) {
        console.error("Registration failed", error);
    }
});
