import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../../features/auth/AuthContext";

const Navbar = () => {
  const { role, token, logout } = useAuth();
  const location = useLocation();

  const linkStyle = (path: string) =>
    `transition ${
      location.pathname === path
        ? "text-white"
        : "text-slate-400 hover:text-white"
    }`;

  return (
    <header className="border-b border-slate-800 bg-slate-900">
      <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">
        <h1 className="text-lg font-semibold tracking-wide">
          EventSphere
        </h1>

        <nav className="flex gap-6 text-sm items-center">
          {token && (
            <>
              {/* Shared */}
              <Link to="/events" className={linkStyle("/events")}>
                Events
              </Link>

              <Link
                to="/my-registrations"
                className={linkStyle("/my-registrations")}
              >
                My Registrations
              </Link>

              {/* HOST Only */}
              {role === "HOST" && (
                <>
                  <Link
                    to="/my-events"
                    className={linkStyle("/my-events")}
                  >
                    My Events
                  </Link>

                  <Link
                    to="/create-event"
                    className={linkStyle("/create-event")}
                  >
                    Create Event
                  </Link>
                </>
              )}
            </>
          )}

          {!token ? (
  <>
    <Link
      to="/auth/login"
      className="text-slate-400 hover:text-white transition"
    >
      Login
    </Link>

    <Link
      to="/auth/register"
      className="text-slate-400 hover:text-white transition"
    >
      Register
    </Link>
  </>
) : (
  <button
    onClick={logout}
    className="text-slate-400 hover:text-red-400 transition"
  >
    Logout
  </button>
)}

        </nav>
      </div>
    </header>
  );
};

export default Navbar;
