import { login } from "../api/api.js";
import {useNavigate} from "react-router-dom";
import {useState} from "react";


export function Login() {

    const navigate = useNavigate();
    const [errorMsg, setErrorMsg] = useState([]);

    function handleSubmit(e) {
        setErrorMsg([]);
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);
        const formJson = Object.fromEntries(formData.entries());

        const username = formJson.username;
        const password = formJson.password;

        login({
            username: username,
            password: password
        }).then(res => {
            console.log(res);
            navigate("/")
        }).catch(error => {
            if (error.response.data.validationErrors) {
                setErrorMsg(error.response.data.validationErrors);
            }
            else {
                setErrorMsg(a => [...a, error.response.data.error]);
            }
        })
    }

    return (
        <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
            <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                {errorMsg.length > 0 &&
                    <div className="alert alert-danger">
                        {errorMsg.map((e, index) => <p key={index}>{e}</p>)}
                    </div>
                }
                <form className="space-y-6" action="#" onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username" className="block text-sm/6 font-medium text-gray-900">Username</label>
                        <div className="mt-2">
                            <input type="text" name="username" id="username" autoComplete="username" required
                                   className="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900
                                    outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400
                                     focus:outline focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600
                                      sm:text-sm/6"/>
                        </div>
                    </div>

                    <div>
                        <div className="flex items-center justify-between">
                            <label htmlFor="password"
                                   className="block text-sm/6 font-medium text-gray-900">Password</label>
                        </div>
                        <div className="mt-2">
                            <input type="password" name="password" id="password" autoComplete="current-password"
                                   required
                                   className="block w-full rounded-md bg-white px-3 py-1.5 text-base
                                    text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300
                                     placeholder:text-gray-400 focus:outline focus:outline-2 focus:-outline-offset-2
                                      focus:outline-indigo-600 sm:text-sm/6"/>
                        </div>
                    </div>

                    <div>
                        <button type="submit"
                                className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6
                                 font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline
                                  focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                            Sign in
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}