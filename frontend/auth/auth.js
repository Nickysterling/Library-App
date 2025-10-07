// ----------------- SIGNUP -----------------
const signupForm = document.getElementById("signup-form");
if (signupForm) {
    signupForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const data = {
            firstName: signupForm.firstName.value,
            lastName: signupForm.lastName.value,
            email: signupForm.email.value,
            password: signupForm.password.value
        };

        try {
            const res = await fetch("http://localhost:8080/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            if (res.ok) {
                alert("Account created! You can now log in.");
                window.location.href = "login.html";
            } else if (res.status === 409) {
                alert("Email already exists. Please use a different one.");
            } else if (res.status === 500) {
                alert("Something went wrong on the server. Please try again later.");
            } else {
                alert("Signup failed. Error code: " + res.status);
            }
        } catch (error) {
            console.error(error);
            alert("Network error during signup");
        }
    });
}

// ----------------- LOGIN -----------------
const loginForm = document.getElementById("login-form");
if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const data = {
            email: loginForm.email.value,
            password: loginForm.password.value
        };

        try {
            const res = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            if (res.ok) {
                alert("Login successful!");
                window.location.href = "../index.html";
            } else if (res.status === 404) {
                alert("User not found!");
            } else if (res.status === 401) {
                alert("Invalid credentials!");
            } else {
                alert("Server error. Try again later.");
            }
        } catch (error) {
            console.error(error);
            alert("Network error during login");
        }
    });
}
