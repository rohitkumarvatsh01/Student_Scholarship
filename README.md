<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Scholarship Eligibility Service</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        header {
            background: #333;
            color: #fff;
            padding-top: 30px;
            min-height: 70px;
            border-bottom: #0779e4 3px solid;
        }
        header a {
            color: #fff;
            text-decoration: none;
            text-transform: uppercase;
            font-size: 16px;
        }
        header ul {
            padding: 0;
            list-style: none;
        }
        header ul li {
            display: inline;
            padding: 0 20px 0 20px;
        }
        .main {
            padding: 15px;
            margin-top: 30px;
            background: #fff;
        }
        footer {
            background: #333;
            color: #fff;
            text-align: center;
            padding: 10px;
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <header>
        <div class="container">
            <h1>Student Scholarship Eligibility Service</h1>
        </div>
    </header>
    <div class="container main">
        <h2>Overview</h2>
        <p>This application allows you to upload student records via a CSV file, assess their eligibility for scholarships based on specified criteria, and retrieve or download these records.</p>
        <h2>Features</h2>
        <ul>
            <li>Upload CSV File</li>
            <li>Get Eligibility Status</li>
            <li>Get All Records</li>
            <li>Download Records as CSV</li>
        </ul>
        <h2>Installation</h2>
        <ol>
            <li>Clone the repository</li>
            <li>Set up the database</li>
            <li>Build the project</li>
            <li>Run the application</li>
        </ol>
        <h2>Usage</h2>
        <h3>1. Upload CSV File</h3>
        <p>URL: <code>/students/upload</code></p>
        <p>Method: POST</p>
        <p>Content-Type: multipart/form-data</p>
        <h3>2. Get Eligibility Status by Roll Number</h3>
        <p>URL: <code>/students/eligible/{rollNumber}</code></p>
        <p>Method: GET</p>
        <h3>3. Get All Student Records</h3>
        <p>URL: <code>/students/eligible</code></p>
        <p>Method: GET</p>
        <h3>4. Download Student Records as CSV</h3>
        <p>URL: <code>/students/download</code></p>
        <p>Method: GET</p>
    </div>
    <footer>
        <p>Student Scholarship Eligibility Service &copy; 2024</p>
    </footer>
</body>
</html>
