const Navbar = () => {
  return (
    <header className="border-b border-slate-800 bg-slate-900">
      <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">
        <h1 className="text-lg font-semibold tracking-wide">
          EventSphere
        </h1>

        <nav className="flex gap-6 text-sm text-slate-400">
          <a className="hover:text-white transition">Events</a>
          <a className="hover:text-white transition">Dashboard</a>
          <a className="hover:text-white transition">Login</a>
        </nav>
      </div>
    </header>
  );
};
export default Navbar;