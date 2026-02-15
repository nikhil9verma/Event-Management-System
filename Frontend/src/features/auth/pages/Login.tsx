import React, { useState } from 'react'
import { useAuth } from '../AuthContext';
import { useNavigate } from 'react-router-dom';
import {api} from "../../../lib/api"

const Login = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    const [email, setemail] = useState("");
    const [password, setpassword] = useState("");
    const [loading, setloading] = useState(false);
    const [error, seterror] = useState<string | null>(null);

    const handleSubmit = async (e:React.FormEvent)=>{
        e.preventDefault();
        setloading(true);
        seterror(null);
    
    try {
      const res = await api.post("/auth/login",{
        email,
        password
      });

      login(res.data.token);
      navigate("/");
    } catch (err:any) {
      seterror(err.response?.data || "Login failed");
    }
    finally{
      setloading(false);
    }
  }

  return (
    <div className='flex items-center justify-center min-h-[70vh]'>
      <form onSubmit={handleSubmit}
      className='bg-slate-900 border border-slate-800 p-8 rounded-2xl w-96 space-y-6'
      >
        <h2 className='text-xl font-semibold text-center'>Login</h2>
        {
          error&&(
            <div className='text-sm text-red-400 text-center'>
              {error}
            </div>
          )
        }
        <div>
          <input 
          type="email" 
          placeholder='Email'
          className='w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2 focus:outline-none focus:border-indigo-500'
           value={email}
            onChange={(e) => setemail(e.target.value)}
            required
          />
        </div>
        <div>
          <input 
          type="password"
          placeholder='Password'
          className='w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2 focus:outline-none focus:border-indigo-500'
           value={password}
            onChange={(e) => setpassword(e.target.value)}
            required
          />
        </div>
         <button
          type="submit"
          disabled={loading}
          className="w-full bg-indigo-600 hover:bg-indigo-500 transition rounded-lg py-2 font-medium disabled:opacity-50"
        >
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>
    </div>
  )
}

export default Login
