class ValueHints < Hobo::ViewHints

  field_names :modes => "Modes to include in:",
				:systems => "Systems to include in:"

  children :systems
end