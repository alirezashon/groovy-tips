// import { NextApiRequest, NextApiResponse } from 'next';

// const URL = 'https://my.mobinnet.ir'; // Replace with your desired URL
// const username = '09304721160'; // Replace with your username
// const password = 'Alireza1380'; // Replace with your password

// // Function to fetch the URL and get the HTML content
// async function fetchURL(): Promise<string> {
//   try {
//     const response = await fetch(URL);
//     const html = await response.text();
//     return html;
//   } catch (error) {
//     console.error('Error fetching URL:', error);
//     throw error;
//   }
// }

// // Function to perform the POST request with your data
// async function performPostRequest(html: string): Promise<string> {
//   // Use a library like Cheerio to parse the HTML and find the desired elements
//   // For example, you can use Cheerio to find an input field's ID and make a POST request with your data

//   // Replace the following code with your own logic using Cheerio to find the desired elements

//   // Example: Finding an input field with ID 'username'
//   const usernameElement = 'input#vs-input--username';

//   // Example: Finding an input field with ID 'password'
//   const passwordElement = 'input#vs-input--password';

//   // Example: Finding the form and action URL
//   const formAction = 'https://my.mobinnet.ir/Account/SignIn'; // Replace with your form action URL

//   // Example: Creating the payload for the POST request
//   const payload = {
//     username,
//     password
//   };

//   try {
//     const response = await fetch(formAction, {
//       method: 'POST',
//       body: JSON.stringify(payload),
//       headers: {
//         'Content-Type': 'application/json'
//       }
//     });

//     const data = await response.text();
//     console.log('POST request successful:', data);
//     return data;
//   } catch (error) {
//     console.error('Error performing POST request:', error);
//     throw error;
//   }
// }

// export default async function handler(req: NextApiRequest, res: NextApiResponse) {
//   try {
//     const html = await fetchURL();
//     const data = await performPostRequest(html);

//     // Modify the code to format and send the data in the desired format (text, JSON, etc.)
//     const responseData = { message: 'Success', data };

//     res.status(200).json(responseData);
//   } catch (error) {
//     console.error('Error scraping website:', error);
//     res.status(500).json({ message: 'Error scraping website' });
//   }
// }






import { NextApiRequest, NextApiResponse } from 'next';
import cheerio from 'cheerio';

const URL = 'https://lms.iranian.ac.ir/login/index.php'; // Replace with your desired URL
const TARGET_URL = 'https://lms.iranian.ac.ir/my/'; // Replace with the URL of the page you want to see after logging in

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  try {
    // Fetch the login page
    const response = await fetch(URL);
    const html = await response.text();

    // Load the HTML into Cheerio
    const $ = cheerio.load(html);

    // Get the logintoken value
    const loginToken = $('input[name="logintoken"]').val("KeCxEWHOm6tAKvtQnR6qAxHlpQ9oupHS");

    // Find the input elements by ID and set their values
    const usernameElement = $('#username');
    const passwordElement = $('#password');
    usernameElement.val('4011650025');
    passwordElement.val('Alireza1380');

    // Get the form action URL
    const formElement = $('#login');
    const formAction = formElement.attr('action');

    if (!formAction) {
      throw new Error('Form action URL not found');
    }

    // Create the form data payload
    const formData = new URLSearchParams();
    formData.append('logintoken', String(loginToken));
    formData.append('username', String(usernameElement.val() || ''));
    formData.append('password', String(passwordElement.val() || ''));

    // Submit the form by sending a POST request
    await fetch(formAction, {
      method: 'POST',
      body: formData,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });

    // Wait for the action to complete and fetch the target page
    await delay(2000); // Adjust the delay as per your needs or use more appropriate waiting methods

    // Fetch the target page
    const targetResponse = await fetch(TARGET_URL);
    const targetHtml = await targetResponse.text();

    // Load the target HTML into Cheerio
    const $target = cheerio.load(targetHtml);

    // Extract data or perform further operations based on the target page

    // Example: Extracting data from a specific element
    const resultElement = $target('#instance-20077-header'); // Adjust the selector based on the target page
    const result = resultElement.text().trim(); // Trim any leading/trailing whitespace

    // Return the result as the API response
    res.status(200).json({ message: 'Action performed successfully', result, targetHtml });
  } catch (error) {
    console.error('Error performing action:', error);
    res.status(500).json({ error: 'Error performing action' });
  }
}






// import { NextApiRequest, NextApiResponse } from "next"

// import cheerio from "cheerio"

// const URL = "https://lms.iranian.ac.ir/login/index.php" // Replace with your desired URL

// export default async function handler(
//   req: NextApiRequest,
//   res: NextApiResponse
// ) {
//   try {
//     // Fetch the page
//     const response = await fetch(URL)
//     const html = await response.text()

//     // Load the HTML into Cheerio
//     const $ = cheerio.load(html)

//     // Find the input element by ID and set its value
//     const inputElement = $("#username")
//     inputElement.val("4011650025")
//     const pass = $("#password")
//     pass.val("Alireza1380")

//     // Simulate a button click event
//     const buttonElement = $("#loginbtn")
//     const buttonClickScript = buttonElement.attr("onclick")
//     if (buttonClickScript) {
//       // Evaluate the button's onclick script to simulate the click event
//       eval(buttonClickScript)
//     }

//     // Wait for the action to complete and fetch the updated page or handle the response

//     // Extract data or perform further operations based on the updated page or response

//     // Example: Extracting data from a specific element
//     const resultElement = $("#instance-20077-header")
//     const ali = $("a")
//     const result = ali.text()

//     // Return the result as the API response
//     res.status(200).json({ message: "Action performed successfully", result })
//   } catch (error) {
//     console.error("Error performing action:", error)
//     res.status(500).json({ error: "Error performing action" })
//   }
// }
