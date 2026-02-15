import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../lib/api";

const CreateEvent = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    title: "",
    description: "",
    date: "",
    time: "",
    venue: "",
    category: "",
    maxParticipants: "",
    registrationDeadline: "",
    posterUrl: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const combineDateTime = (date: string, time: string) => {
    return `${date}T${time}:00`;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const eventDateTime = combineDateTime(
        formData.date,
        formData.time
      );

      await api.post("/events", {
        title: formData.title,
        description: formData.description,
        eventDate: eventDateTime,
        eventTime: eventDateTime,
        venue: formData.venue,
        category: formData.category,
        maxParticipants: Number(formData.maxParticipants),
        registrationDeadline: formData.registrationDeadline,
        posterUrl: formData.posterUrl,
      });

      navigate("/");
    } catch (err: any) {
      setError(
        err.response?.data?.message ||
          "Failed to create event"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto mt-12">
      <form
        onSubmit={handleSubmit}
        className="bg-slate-900 border border-slate-800 p-10 rounded-3xl space-y-8 shadow-lg"
      >
        <h2 className="text-3xl font-semibold text-center">
          Create New Event
        </h2>

        {error && (
          <div className="text-red-400 text-center text-sm">
            {error}
          </div>
        )}

        {/* Title */}
        <div>
          <label className="block mb-2 text-sm text-slate-400">
            Title
          </label>
          <input
            name="title"
            className="w-full bg-slate-800 p-3 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>

        {/* Description */}
        <div>
          <label className="block mb-2 text-sm text-slate-400">
            Description
          </label>
          <textarea
            name="description"
            rows={4}
            className="w-full bg-slate-800 p-3 rounded-xl focus:outline-none focus:ring-2 focus:ring-indigo-500"
            value={formData.description}
            onChange={handleChange}
          />
        </div>

        {/* Date & Time */}
        <div className="grid md:grid-cols-2 gap-6">
          <div>
            <label className="block mb-2 text-sm text-slate-400">
              Event Date
            </label>
            <input
              type="date"
              name="date"
              className="w-full bg-slate-800 p-3 rounded-xl"
              value={formData.date}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <label className="block mb-2 text-sm text-slate-400">
              Event Time
            </label>
            <input
              type="time"
              name="time"
              className="w-full bg-slate-800 p-3 rounded-xl"
              value={formData.time}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        {/* Venue & Category */}
        <div className="grid md:grid-cols-2 gap-6">
          <input
            name="venue"
            placeholder="Venue"
            className="bg-slate-800 p-3 rounded-xl"
            value={formData.venue}
            onChange={handleChange}
            required
          />

          <input
            name="category"
            placeholder="Category"
            className="bg-slate-800 p-3 rounded-xl"
            value={formData.category}
            onChange={handleChange}
            required
          />
        </div>

        {/* Max Participants */}
        <input
          type="number"
          name="maxParticipants"
          placeholder="Max Participants"
          className="w-full bg-slate-800 p-3 rounded-xl"
          value={formData.maxParticipants}
          onChange={handleChange}
          required
        />

        {/* Registration Deadline */}
        <div>
          <label className="block mb-2 text-sm text-slate-400">
            Registration Deadline
          </label>
          <input
            type="datetime-local"
            name="registrationDeadline"
            className="w-full bg-slate-800 p-3 rounded-xl"
            value={formData.registrationDeadline}
            onChange={handleChange}
            required
          />
        </div>

        {/* Poster URL */}
        <input
          name="posterUrl"
          placeholder="Poster Image URL (optional)"
          className="w-full bg-slate-800 p-3 rounded-xl"
          value={formData.posterUrl}
          onChange={handleChange}
        />

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-indigo-600 hover:bg-indigo-500 transition py-3 rounded-xl font-semibold disabled:opacity-50"
        >
          {loading ? "Creating Event..." : "Create Event"}
        </button>
      </form>
    </div>
  );
};

export default CreateEvent;
